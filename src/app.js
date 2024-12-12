require('dotenv').config()
const PORT = process.env.PORT || 5000;
const express = require('express');

const usersRoutes = require('./routes/userRoutes');

const app = express();

app.use(express.json());

app.use('/api/users', usersRoutes);

app.use((err, req, res, next) => {
    res.json({
        message: err.message
    })
})

app.listen(PORT, () => {
    console.log(`Server running on http://localhost:${PORT}/api/users`);
});