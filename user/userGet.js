const express = require('express')
const router = express.Router()
const bodyParser = require('body-parser')
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

router.get('/whatsappclone/api/user', (req,res)=>{
    const username = req.body.username
    const password = req.body.password


    if(username != undefined || username != "" || username != null){
        if(password != undefined || password != "" || password != null){
            const command = 'select * from login where username = ? and password = ?'
            mySqlDatabase.query(command,[username, password], (err, rows, fields)=>{
                if(err){
                    console.log('error with '+err)
                }else{
                    res.send(rows)
                }
            })
        }else{
            const command = 'select * from login where username = ?'
            mySqlDatabase.query(command,[username], (err, rows, fields)=>{
                if(err){
                    console.log('error with '+err)
                }else{
                    res.send(rows)
                }
            })
        }        
    }else {
       const usernameQuery = 'select * from login'
       mySqlDatabase.query(usernameQuery, (err, rows, fields)=>{
           if(err){
               console.log('error with '+err)
           }else{
               res.send(rows)
           }
       })
    }
}) //router get

module.exports = router