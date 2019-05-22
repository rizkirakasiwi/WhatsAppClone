const express = require('express')
const router = express.Router()
const bodyParser = require('body-parser')
const fs = require('fs')
const Joi = require('joi')
const underscore = require('underscore')

router.use(bodyParser.json())
router.use(bodyParser.urlencoded({extended:true}))

function validasiInput(body){
    const schema = {
        id:Joi.string().required(),
        username: Joi.string().min(3),
        password:Joi.string().min(6),
        nama:Joi.string().min(3),
        email:Joi.string(),
        noHp:Joi.string().min(10),
        caption:Joi.string()
    }

    return Joi.validate(body, schema)
}


router.put('/whatsappclone/api/user', (req, res)=>{
    const uid = req.body.id
    const username = req.body.username
    const password = req.body.password
    const nama = req.body.nama
    const noHp = req.body.noHp
    const email = req.body.email
    const caption = req.body.caption


    const {error} = validasiInput(req.body)
    if(error) return res.status(400).send(error.details[0].message)

    

    fs.readFile('./user/userData.json', 'utf8',function(err, data){
        const output = JSON.parse(data)
        const filterUsername = underscore.where(output, {id:uid})
        console.log(filterUsername);
    
        
        if(filterUsername==""){
            console.log("User tidak ditemukan");
            res.send("User tidak ditemukan")
        }else{
            
            for(var i in output){
                if(output[i].id === uid){
                    if(username==null){
                        console.log("filter :   "+output[i].username);
                        output[i].username = output[i].username 
                        output[i].password = password
                        output[i].nama = nama
                        output[i].noHp = noHp
                        output[i].email = email
                        output[i].caption = caption
                    
                        console.log(output)
                        const sendJSON = JSON.stringify(output, null, 2)
                        
                        fs.writeFile('./user/userData.json', sendJSON, function(err){
                            if (err)throw err;
                            res.send("Data berhasil di update")
                        })
                    }
                }else{
                    if(output[i].id === uid){
                        if(output[i].username === username){
                            res.send("Username sudah digunakan")
                        }else{
                        
                                console.log('username === username');
                                output[i].username = username
                                output[i].password = password
                                output[i].nama = nama
                                output[i].noHp = noHp
                                output[i].email = email
                                output[i].caption = caption
                            
                                console.log(output)
                                const sendJSON = JSON.stringify(output, null, 2)
                                
                                fs.writeFile('./user/userData.json', sendJSON, function(err){
                                    if (err)throw err;
                                    res.send("Data berhasil di update")
                                })
                        }
                    }
                }
            }    
        }
    })

    
})


module.exports = router