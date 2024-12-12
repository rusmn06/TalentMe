require('dotenv').config();
const mysql = require('mysql2');

const dbPool = mysql.createPool({
    socketPath: process.env.INSTANCE_UNIX_SOCKET,
    user: process.env.DB_USER,
    password: process.env.DB_PASS,
    database: process.env.DB_NAME,
});

const promisePool = dbPool.promise();

module.exports = promisePool;
