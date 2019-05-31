const express = require('express')
const router = express.Router()
const bodyParser = require('body-parser')
const Joi = require('joi')
const fs = require('fs')
const uid = require('uuid/v4')
const multer = require('multer')



router.use(bodyParser.json())
router.use(bodyParser.urlencoded({extended:true}))

function validasiInput(body){
    const schema = {
        username: Joi.string().min(3).required(),
        password:Joi.string().min(6).required(),
        nama:Joi.string().min(3).required(),
        email: Joi.string(),
        noHp:Joi.string().min(10).required(),
        caption:Joi.string(),
        status:Joi.string(),
        waktuOnline:Joi.string()
    }

    return Joi.validate(body, schema)
}


// var form = "<!DOCTYPE HTML><html><body>" +
// "<form method='post' action='/upload' enctype='multipart/form-data'>" +
// "<input type='file' name='upload'/>" +
// "<input type='submit' /></form>" +
// "</body></html>";


const storage = multer.diskStorage({
    destination: function(req, file, cb){
        cb(null, 'user/uploads')
    },
    filename: function(req, file, cb){
        cb(null, uid()+'.png')
    }
})


const upload = multer({storage:storage})


// router.get('/upload', (req, res)=>{
//     res.writeHead(200, {'Content-Type': 'text/html' });
//     res.end(form);
// })


// router.post('/upload', upload.single('upload'), function (req, res, next) {
//     // req.file is the `avatar` file
//     // req.body will hold the text fields, if there were any
//     if(!req.file){
//         res.send("data null")
//     }
//     else if(req.file){
//         res.json(req.file)
//     }
// })

router.post('/whatsappclone/api/user',upload.single('upload'), (req, res)=>{
    const key = uid()
    console.log(key)
    const username = req.body.username
    const password = req.body.password
    const nama = req.body.nama
    const noHp = req.body.noHp
    const email = req.body.email
    const caption = req.body.caption
    const status = req.body.status
    const waktuOnline = req.body.waktuOnline
    var imageName = ""

    if(req.file){
        imageName = req.file.filename
    }else{
        imageName = "null"
    }

    const {error} = validasiInput(req.body)
    if(error) return res.status(400).send(error.details[0].message)

    const sendData = [{
        id:key,
        username:username,
        password:password,
        nama:nama,
        noHp:noHp,
        email:email,
        caption:caption,
        status:status,
        waktuOnline:waktuOnline,
        imageName
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