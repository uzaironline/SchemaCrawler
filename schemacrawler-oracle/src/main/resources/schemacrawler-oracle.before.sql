-- APPEND A SQL TERMINATOR TO EACH DDL STATEMENT
{call DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.SESSION_TRANSFORM, 'SQLTERMINATOR', TRUE)};
-- EMIT THE BYTE KEYWORD AS PART OF THE SIZE SPECIFICATION OF CHAR AND VARCHAR2 COLUMNS THAT USE BYTE SEMANTICS
{call DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.SESSION_TRANSFORM, 'SIZE_BYTE_KEYWORD', TRUE)};
-- SPECIFY THAT STORAGE CLAUSES ARE NOT TO BE RETURNED IN THE SQL DDL
{call DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.SESSION_TRANSFORM, 'SEGMENT_ATTRIBUTES', FALSE)};
{call DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.SESSION_TRANSFORM, 'PARTITIONING', FALSE)};
{call DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.SESSION_TRANSFORM, 'SPECIFICATION', FALSE)};
