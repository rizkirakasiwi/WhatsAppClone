const express = require('express')
const router = express.Router()
const bodyParser = require('body-parser')
const Joi = require('joi')
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

router.post('/whatsappclone/api/user', (req, res)=>{
    const username = req.body.username
    const password = req.body.password
    const nama = req.body.nama
    const noHp = req.body.noHp
    const caption = req.body.caption

    const {error} = validasiInput(req.body)
    if(error) return res.send(400).status(error.details[0].message)

    var login = []
    login.push(
        username,
        password,
        nama,
        noHp,
        caption
    )
    // const {error} = validasiInput(req.body)
   const query = "insert into login(username,password,nama,noHp,caption) values(?);select * from login where username=?"
   mySqlDatabase.query(query, [login,username], (err, rows, fields)=>{
       if(err){
           console.log("errorwith "+err)
           res.send(err.message)
       }else{
           console.log("Post berhasil")
           res.send(rows[1])
       }
   })
})

module.exports = router