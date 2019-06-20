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
        id:Joi.string(),
        username: Joi.string().min(3).required(),
        password:Joi.string().min(6).required(),
        nama:Joi.string(),
        email: Joi.string(),
        noHp:Joi.string(),
        caption:Joi.string(),
        status:Joi.string(),
        imageName:Joi.string(),
        waktuOnline:Joi.string()
    }

    return Joi.validate(body, schema)
}


var form = "<!DOCTYPE HTML><html><body>" +
"<form method='post' action='/upload' enctype='multipart/form-data'>" +
"<input type='file' name='upload'/>" +
"<input type='submit' /></form>" +
"</body></html>";



const upload = multer({ storage: multer.memoryStorage({}) })


router.get('/whatsappclone/api/user/upload', (req, res)=>{
    res.writeHead(200, {'Content-Type': 'text/html' });
    res.end(form);
})


router.post('/whatsappclone/api/user/upload', upload.single('upload'), 
function (req, res, next) {
    if(req.file){
        var raw = new Buffer(req.file.buffer.toString(), 'base64')
        const filename = uid()
        fs.writeFile(`user/uploads/${filename}.jpeg`, raw, function(err){
            if(err){
                console.log("error with : "+err.message)
            }else{
                res.send(filename)
                console.log("filename : "+filename)
            }
        })
    }else{
        console.log("req.file is undefined")
        res.send("req.file is null")
    }
})

router.post('/whatsappclone/api/user', (req, res)=>{
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
    var imageName = req.body.imageName

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
            res.send("User berhasil ditambahkan!")
            });
        }
    
      });
})

module.exports = router
