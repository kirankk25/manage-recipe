CREATE TABLE TB_USER
(   ID INTEGER, USER_NAME varchar(32), PASSWORD varchar(100), ENABLED varchar(5));
CREATE TABLE TB_REFRESH_TOKEN
(   ID INTEGER, TOKEN varchar(32), CREATE_DATTIME datetime);
CREATE TABLE TB_RECIPE
(ID INTEGER,RECIPE_CODE VARCHAR(10),RECIPE_NAME VARCHAR(50),RECIPE_TYPE VARCHAR(10),SERVINGS INTEGER,INGREDIENTS VARCHAR(50),INSTRUCTIONS VARCHAR(50));


