const dbPool = require('../config/db');


const getAllUsers = async () => {
    const query = 'SELECT * FROM user';
    const [rows] = await dbPool.execute(query);
    return rows;
};

const getUserById = (idUser) => {
    const query = `SELECT id_user, nama, email FROM user WHERE id_user = ? LIMIT 1`;
    return dbPool.execute(query, [idUser]);
};

const updateUser = async (body, idUser) => {
    const query = 'UPDATE user SET nama = ?, jenis_kelamin = ?, tgl_lahir = ?, email = ?, password = ? WHERE id_user = ?';
    const { nama, jenis_kelamin, tgl_lahir, email, password } = body;
    const [result] = await dbPool.execute(query, [nama, jenis_kelamin, tgl_lahir, email, password, idUser]);
    return result;
};

const deleteUser = async (idUser) => {
    const query = 'DELETE FROM user WHERE id_user = ?';
    const [result] = await dbPool.execute(query, [idUser]);
    return result;
};

const createUser = async (nama, jenis_kelamin, tgl_lahir, email, password) => {
    const query = 'INSERT INTO user (nama, jenis_kelamin, tgl_lahir, email, password, created_at) VALUES (?, ?, ?, ?, ?, NOW())';
    const [result] = await dbPool.execute(query, [nama, jenis_kelamin, tgl_lahir, email, password]);
    return result;
};

const loginUser = async (email, password) => {
    const query = 'SELECT id_user, nama, email FROM user WHERE email = ? AND password = ?';
    const [results] = await dbPool.execute(query, [email, password]);
    if (results.length === 0) return null;
    return results[0];
};

const saveTestUser = async (id_user, hasil_test) => {
    const query = 'INSERT INTO hasil_test (id_user, hasil_test) VALUES (?, ?)';
    const [result] = await dbPool.execute(query, [id_user, hasil_test]);
    return result;
};

const saveSectorUser = async (id_user, id_sektor, urutan, aktivasi) => {
    const query = `INSERT INTO user_sector (id_user, id_sektor, urutan, aktivasi, created_at) VALUES (?, ?, ?, ?, NOW())`;
    const [result] = await dbPool.execute(query, [id_user, id_sektor, urutan, aktivasi]);
    return result;
};

module.exports = {
    getAllUsers,
    createUser,
    getUserById,
    updateUser,
    deleteUser,
    loginUser,
    saveTestUser,
    saveSectorUser,
};
