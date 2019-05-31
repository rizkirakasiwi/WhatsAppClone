const express = require('express')
const router = express.Router()
const bodyParser = require('body-parser')
const fs = require('fs')
const underscore = require('underscore')

const data = fs.readFileSync('./user/userData.json')
const json = JSON.parse(data)


router.use(bodyParser.json())
router.use(bodyParser.urlencoded({extended:true}))

router.get('/whatsappclone/api/loadImageUser/:imageName',(req,res)=>{
    const imageName = req.params.imageName
    fs.createReadStream(`./user/uploads/${imageName}`).pipe(res)
})

router.get('/', (req, res)=>{
    res.send("Welcome to WhatsApp clone API")
})

router.get('/whatsappclone/api/user', (req,res)=>{
    const username = req.body.username
    const password = req.body.password
    
    if(username == null){
       res.send(json)
       console.log(json)
    }else{
        if(password == null){
            const filter = underscore.where(json, {username:username})
            if(filter != ""){
                res.send(filter)
                console.log(filter)
            }else{
                res.send(`Data tidak ditemukan`)
                console.log("Data tidak ditemukan")
            }
        }else{
            const filter = underscore.where(json, {username:username, password:password})
            if(filter != ""){
                res.send(filter)
                console.log("Filter"+JSON.stringify(filter))
            }else{
                res.send("Data tidak ditemukan")
                console.log("Data tidak ditemukan")
            }
        }
    }
    
    
}) //router get

module.exports = router