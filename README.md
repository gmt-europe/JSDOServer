# JSDO Server

LGPL License.

# Introduction

The JSDO Server project is an implementation of the JSDO protocol from the server perspective. This project implements
the parts of the JSDO protocol used by Rollbase.

# Usage

The JSDO Server project implements the JSDO protocol. Data for the implementation is provided by MySQL. This
application expects a local MySQL database to be running with the following configuration:

* A schema named `jsdo_demo`;
* A user named `jsdo_demo` with the password `jsdo_demo`, with access to the schema `jsdo_demo`;
* The SQL schema in `Database.sql` should be used to initialize the `jsdo_demo` schema.

After the database has been setup, the JSDO Server project can be run against this database. The application
starts a web server at the following URL:

* http://localhost:5851/jsdo/

This is the root URL of the JSDO implementation.

To use the JSDO Server project with Rollbase, complete the following steps:

* Download the JSDO catalog at http://localhost:5851/jsdo/catalog.json;
* Go to Rollbase and click the `New` button to add a new tab;
* Check `A new Object (with Tab) from an External Metadata`;
* Check `OpenEdge Service`;
* Select the `catalog.json` file and enter `http://localhost:5851/jsdo/` as the Service URI;
* Review the settings on the `Select Components` page and click `Next`;
* Review the settings on the `Import Objects from OpenEdge Services` page and click `Create`. Note that you need
  to select the record name fields. For the `Relation` object, the `Name` field is automatically selected. For the
  `Address` object you should select `Code` as the name field.

The JSDO catalog is now imported into Rollbase. The JSDO Server projects implements all CRUD operations. From the
automatically created pages you should be able to create, modify and delete records.

# JSDO Specification

The specifications that cover the implementation of the JSDO Server project are available at the
[JSDO Server Wiki](https://github.com/gmt-europe/JSDOServer/wiki).

# Issues

The JSDO Server project is not a production quality JSDO implementation. It is only meant for educational purposes.
However, if you do find any issues with the project, please create a new issue in the
[issues section](https://github.com/gmt-europe/JSDOServer/issues).
