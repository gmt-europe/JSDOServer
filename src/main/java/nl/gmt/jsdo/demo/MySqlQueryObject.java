package nl.gmt.jsdo.demo;

import nl.gmt.jsdo.query.JsdoField;
import nl.gmt.jsdo.query.JsdoQueryObject;
import nl.gmt.jsdo.query.JsdoRecord;
import nl.gmt.jsdo.query.JsdoRecordSet;
import nl.gmt.jsdo.query.parser.AblFilterLexer;
import nl.gmt.jsdo.query.parser.AblFilterParser;
import nl.gmt.jsdo.query.parser.Expression;
import nl.gmt.jsdo.schema.JsdoObject;
import nl.gmt.jsdo.schema.JsdoResource;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.apache.commons.lang.Validate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySqlQueryObject extends JsdoQueryObject {
    private final Connection connection;

    public MySqlQueryObject(JsdoResource resource, JsdoObject object, Connection connection) {
        super(resource, object);

        Validate.notNull(connection, "connection");

        this.connection = connection;
    }

    @Override
    public JsdoRecordSet getRecords(String filter) throws Exception {
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT * FROM `").append(getObject().getName()).append('`');

        if (filter != null) {
            sb.append(" WHERE ");
            sb.append(parseFilter(filter).accept(new FilterVisitor()));
        }

        // Rollbase expects the full record set to be returned every time. The JSDO interface does not support
        // pagination; at least not in the Rollbase implementation.
        // sb.append(" LIMIT 20");

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sb.toString());

            JsdoRecordSet recordSet = new JsdoRecordSet(getResource(), getObject());

            while (resultSet.next()) {
                JsdoRecord record = new JsdoRecord();
                recordSet.getRecords().add(record);

                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    record.getFields().add(new JsdoField(
                        resultSet.getMetaData().getColumnName(i),
                        resultSet.getObject(i)
                    ));
                }
            }

            return recordSet;
        }
    }

    private Expression parseFilter(String filter) throws RecognitionException {
        AblFilterLexer lexer = new AblFilterLexer(new CaseInsensitiveStringStream(filter));
        AblFilterParser parser = new AblFilterParser(new CommonTokenStream(lexer));
        return parser.filter();
    }

    private class CaseInsensitiveStringStream extends ANTLRStringStream {
        public CaseInsensitiveStringStream(String input) {
            super(input);
        }

        @Override
        public int LA(int i) {
            int la = super.LA(i);
            if (la == 0 || la == CharStream.EOF) {
                return la;
            }

            // If we're returning a real character, convert it to upper case. The reason we do this is that this
            // grammar is case insensitive, but the grammar only matches for upper case. By making LA return
            // only upper case characters, we can fake the input being all upper case. We are still returning
            // the correct case from the lexer.
            return Character.toUpperCase(la);
        }
    }
}
