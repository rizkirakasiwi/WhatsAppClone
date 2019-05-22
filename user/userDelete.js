const express = require('express')
const router = express.Router()
const bodyParser = require('body-parser')
const fs = require('fs')


router.use(bodyParser.json())
router.use(bodyParser.urlencoded({extended:true}))




router.delete('/whatsappclone/api/user', (req, res)=>{
    const id = req.body.id

    if(id == null){
        res.send("Delele failed, please make sure your id")
    }else{
       fs.readFile('./user/userData.json', 'utf8',function(err, data){
            const output = JSON.parse(data)
            const dataFilter = output.filter(function(object){
                return object.id !== id
            })

            fs.writeFile('./user/userData.json',JSON.stringify(dataFilter,null,2),function(err){
                if(err) throw err
                res.send("Data berhasil dihapus")
            })
       })
    }
})
module.exports = router