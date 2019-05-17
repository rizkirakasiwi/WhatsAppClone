const express = require('express')
const morgan = require('morgan')
const app = express()

app.use(morgan('combined'))

const loginRouter = require('./user/userDelete.js')
app.use(loginRouter)

const userGet = require('./user/userGet.js')
app.use(userGet)

const userPost = require('./user/userPost.js')
app.use(userPost)

const userPut = require('./user/userPut.js')
app.use(userPut)

const port = process.env.PORT || 2000

app.listen(port, ()=>{
    console.log('listen for port '+port)
})
