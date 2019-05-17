const express = require('express')
const router = express.Router()
const bodyParser = require('body-parser')
const Joi = require('joi')
const mysql = require('mysql')

router.use(bodyParser.json())
router.use(bodyParser.urlencoded({extended:true}))


const mySqlDatabase = mysql.createPool({
    host:'localhost',
    user:'root',
    password:'123qwejkl',
    database:'whatsAppClone',
    multipleStatements:true
})


router.delete('/whatsappclone/api/user', (req, res)=>{
    const username = req.body.username

    if(username == ""||username == null || username == undefined){
        const query = "delete from login"

        mySqlDatabase.query(query, (err, rows, fields)=>{
            if(err){
                res.send("some error")
                console.log('error with '+err.message);
            }else{
                res.send(`deleted success`)
            }
        })
    }else{
        const query = "delete from login where username = ?"
        mySqlDatabase.query(query, [username], (err, rows, fields)=>{
            if(err){
                res.send("some error")
                console.log('error with '+err.message);
            }else{
                res.send(`user ${username} was deleted`)
            }
        })
    }
})
module.exports = router