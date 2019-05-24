const express = require('express')
const router = express.Router()
const bodyParser = require('body-parser')
const Joi = require('joi')
const fs = require('fs')
const jsonfile = require('jsonfile')
const uid = require('uuid/v4')



router.use(bodyParser.json())
router.use(bodyParser.urlencoded({extended:true}))

function validasiInput(body){
    const schema = {
        username: Joi.string().min(3).required(),
        password:Joi.string().min(6).required(),
        nama:Joi.string().min(3).required(),
        email: Joi.string(),
        noHp:Joi.string().min(10).required(),
        caption:Joi.string()
    }

    return Joi.validate(body, schema)
}






router.post('/whatsappclone/api/user', (req, res)=>{
    const key = uid()
    console.log(key)
    const username = req.body.username
    const password = req.body.password
    const nama = req.body.nama
    const noHp = req.body.noHp
    const email = req.body.email
    const caption = req.body.caption

   

    const {error} = validasiInput(req.body)
    if(error) return res.status(400).send(error.details[0].message)

    const sendData = [{
        id:key,
        username:username,
        password:password,
        nama:nama,
        noHp:noHp,
        email:email,
        caption:caption
    }]
           
    fs.readFile('./user/userData.json', 'utf8', function (err, data) {
        if (err) throw err;
        const output = JSON.parse(data);
        var keterangan = ""

        for(var i in output){
            if(output[i].username === username){
                keterangan = "Username already exist"
            }else if(output[i].id===key){
                keterangan="ID sudah digunakan"
            }
        }

        if(keterangan != ""){
            res.send(keterangan)
        }else{
            output.push(sendData[0]);
    
            const sendJSON = JSON.stringify(output, null, 2);
            fs.writeFile('./user/userData.json', sendJSON, function(err){
            if (err) throw err;
            res.send(sendData)
            });
        }
    
      });
})

module.exports = router