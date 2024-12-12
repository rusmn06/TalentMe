const userModels = require('../models/userModel');

const getAllUsers = async (req, res) => {
    try {
        const user = await userModels.getAllUsers();
        res.status(200).json({
            message: 'Berhasil mendapatkan semua user',
            data: user, 
        });
    } catch (error) {
        res.status(500).json({
            message: 'Gagal mendapatkan user, Server Error',
            serverMessage: error.message,
        });
    }
};

const getUserById = async (req, res) => {
    const { idUser } = req.params;
    try {
        if (!idUser) {
            return res.status(400).json({
                message: 'User tidak ditemukan'
            });
        }

        const [rows] = await userModels.getUserById(idUser);
        if (!rows || rows.length === 0) {
            return res.status(404).json({
                message: `Gagal mendapatkan user dengan ID ${idUser}`,
            });
        }

        res.status(200).json({
            message: `Berhasil mendapatkan user dengan ID ${idUser}`,
            data: rows[0], // Menggunakan row pertama dari hasil query
        });
    } catch (error) {
        res.status(500).json({
            message: 'Gagal mendapatkan user, Server Error',
            serverMessage: error.message,
        });
    }
};

const updateUser = async (req, res) => {
    const { idUser } = req.params;
    const { body } = req;
    try {
        await userModels.updateUser(body, idUser);
        res.status(200).json({
            message: 'Berhasil memperbaharui user',
            data: {
                id: idUser,
                ...body,
            },
        });
    } catch (error) {
        res.status(500).json({
            message: 'Gagal memperbaharui user, mohon periksa kembali',
            serverMessage: error.message,
        });
    }
};

const deleteUser = async (req, res) => {
    const { idUser } = req.params;
    try {
        await userModels.deleteUser(idUser);
        res.status(200).json({
            message: 'Berhasil menghapus user',
            data: null,
        });
    } catch (error) {
        res.status(500).json({
            message: 'Gagal menghapus user, mohon periksa kembali',
            serverMessage: error.message,
        });
    }
};

const createUser = async (req, res) => {
    const { nama, jenis_kelamin, tgl_lahir, email, password } = req.body;
    try {
        const result = await userModels.createUser(nama, jenis_kelamin, tgl_lahir, email, password);
        res.status(201).json({
            message: 'Berhasil membuat akun',
            userId: result.insertId,
        });
    } catch (error) {
        res.status(500).json({
            message: 'Gagal membuat akun, kesalahan server',
            serverMessage: error.message,
        });
    }
};

const loginUser = async (req, res) => {
    const { email, password } = req.body;
    try {
        const user = await userModels.loginUser(email, password);
        if (!user) {
            return res.status(401).json({
                message: 'Email dan Password tidak sesuai',
            });
        }
        res.status(200).json({
            message: 'Berhasil Login',
            user,
        });
    } catch (error) {
        res.status(500).json({
            message: 'Gagal Login, kesalahan server',
            serverMessage: error.message,
        });
    }
};

const saveTestUser = async (req, res) => {
    const { id_user, hasil_test } = req.body;
    if (!id_user || !hasil_test) {
        return res.status(400).json({
            message: 'Kesalahan, kirim ulang data',
            data: null,
        });
    }
    try {
        const result = await userModels.saveTestUser(id_user, hasil_test);
        res.status(201).json({
            message: 'Berhasil menyimpan Hasil Test',
            data: { id_user, hasil_test },
        });
    } catch (error) {
        res.status(500).json({
            message: 'Gagal menyimpan Hasil Test, kesalahan server',
            serverMessage: error.message,
        });
    }
};

const saveSectorUser = async (req, res) => {
    const { id_user, id_sektor, urutan, aktivasi } = req.body;
    if (!id_user || !id_sektor || !urutan || !aktivasi) {
        return res.status(400).json({
            message: 'Kesalahan, kirim ulang data',
            data: null,
        });
    }
    try {
        const result = await userModels.saveSectorUser(id_user, id_sektor, urutan, aktivasi);
        res.status(201).json({
            message: 'Berhasil menyimpan User Sektor',
            data: { id_user, id_sektor, urutan, aktivasi },
        });
    } catch (error) {
        res.status(500).json({
            message: 'Gagal menyimpan User Sektor, kesalahan server',
            serverMessage: error.message,
        });
    }
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
