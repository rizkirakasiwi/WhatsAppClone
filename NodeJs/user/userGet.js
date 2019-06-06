const express = require('express')
const router = express.Router()
const bodyParser = require('body-parser')
const fs = require('fs')

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
       res.send(json)
       console.log(json) 
}) //router get

module.exports = router