const express = require('express')
const router = express.Router()
const bodyParser = require('body-parser')
const mysql = require('mysql')

router.use(bodyParser.json())
router.use(bodyParser.urlencoded({extended:true}))

function validasiInput(body){
    const schema = {
        username: Joi.string().min(3).required(),
        password:Joi.string().min(6).required(),
        nama:Joi.string().min(3).required(),
        noHp:Joi.string().min(10).required(),
        caption:Joi.string()
    }

    return Joi.validate(body, schema)
}

const mySqlDatabase = mysql.createPool({
    host:'localhost',
    user:'root',
    password:'123qwejkl',
    database:'whatsAppClone',
    multipleStatements:true
})


router.put('/whatsappclone/api/user', (req, res)=>{
    const username = req.body.username
    const password = req.body.password
    const nama = req.body.nama
    const noHp = req.body.noHp
    const caption = req.body.caption

    const {error} = validasiInput(req.body)
    if(error) return res.status(400).send(error.details[0].message)


    const query = "update login set password=?, nama =?, noHp=?, caption=? where username = ?; select * from login where username = ?"

    mySqlDatabase.query(query, [password,nama,noHp, caption, username, username], (err, rows, fields)=>{
        if(err){
            res.send('Some error')
            console.log('error with '+err.message)
        }else{
            res.send(rows[1])
        }
    })
})


module.exports = router