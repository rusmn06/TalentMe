const express = require('express');

const {
    getAllUsers,
    createUser,
    getUserById,
    updateUser,
    deleteUser,
    loginUser,
    saveTestUser,
    saveSectorUser,
} = require('../controllers/userController');

const router = express.Router();

router.get('/', getAllUsers);
router.get('/:idUser', getUserById);
router.put('/:idUser', updateUser);
router.delete('/:idUser', deleteUser);

// endpoint aplikasi
router.post('/register', createUser);
router.post('/login', loginUser);
router.post('/hasil-test', saveTestUser);
router.post('/user-sektor', saveSectorUser);

module.exports = router;