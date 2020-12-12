/*
RESTFUL Services by NodeJS
Author: Yafet Shil
*/
var crypto = require('crypto');
var uuid = require ('uuid');
var express = require('express');
var mysql = require('mysql');
var bodyParser = require('body-parser');

//Connect to MySQL
var con = mysql.createConnection({
    host:'localhost',
    port: '3306',
    user: 'root',
    password: '',
    connector: 'mysql',
    database: 'tunguide',
});
con.connect((err)=> {
    if(!err)
        console.log('Connection Established Successfully');
    else
        console.log('Connection Failed!'+ JSON.stringify(err,undefined,2));
});

//PASSWORD CRYPT
var genRandomString = function (length) {
    return crypto.randomBytes(Math.ceil(length/2))
        .toString('hex') //Convert to hexa format
        .slice(0,length);
    
};
var sha512 = function (password,salt) {
    var hash = crypto.createHmac('sha512',salt) ; //Use SHA512
    hash.update(password);
    var value = hash.digest('hex');
    return {
        salt:salt,
        passwordHash:value
    };
    
};
function saltHashPassword(userPassword) {
    var salt = genRandomString(16); //Gen Random string with 16 charachters
    var passwordData = sha512(userPassword,salt) ;
    return passwordData;
    
}
function checkHashPassword(userPassword,salt) {
    var passwordData = sha512(userPassword,salt);
    return passwordData;
    
}


var app = express();
app.use(bodyParser.json()); // Accept JSON params
app.use(bodyParser.urlencoded({extended:true})); //Accept UrlEncoded params




var multer = require('multer');
var upload_image = multer({dest:'images'});

app.use(express.static('public'));

//Serves all the request which includes /images in the url from Images folder
app.use('/images', express.static(__dirname + '/images'));

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));


app.post('/upload_image', upload_image.single('profile_image'), (req, res) => {
  try {
    res.send(req.file);
  }catch(err) {
    res.send(400);
  }
});




app.patch('/updatephoto/',(req,res,next)=>{


	var id, photo;
	id = req.query.id;
	photo = req.query.ImageUrl;

    con.query('UPDATE user set image_url = ? WHERE id = ?',[photo, id],function (err,result,fields) {
        con.on('error',function (err) {
            console.log('[MYSQL ERROR]',err);
            res.json('succs');
        });
        if (result && result.length)
            res.json('User already exists!!!');
        else {
           
        }
    });

})


app.post('/login/',(req,res,next)=>{
    var post_data = req.body;

    //Extract email and password from request
    var user_password = post_data.password;
    var email = post_data.email;

    con.query('SELECT * FROM user where email=?',[email],function (err,result,fields) {
        con.on('error', function (err) {
            console.log('[MYSQL ERROR]', err);
        });
        if (result && result.length)

        {
            var salt = result[0].salt;
            var encrypted_password = result[0].encrypted_password;
            var hashed_password = checkHashPassword(user_password, salt).passwordHash;
			if (encrypted_password == hashed_password)
			{
				res.end(JSON.stringify(result[0]))
				console.log('User Connected : '+ email);
			}
			else
				res.end(JSON.stringify('Wrong Password'))


        }

        else {

                res.json('user does not exists!!');

            }

    });


})




app.post('/register/',(req,res,next)=>{
    var {name, lastName, email, phoneNumber, password}  = req.body;  //Get POST params
    var uid = uuid.v4();   //Get  UUID V4
    var plaint_password = password ;  //Get password from post params
    var hash_data = saltHashPassword(plaint_password);
    password = hash_data.passwordHash;  //Get Hash value
    var salt = hash_data.salt; //Get salt



    con.query('SELECT * FROM user where email=?',[email],function (err,result,fields) {
        con.on('error',function (err) {
            console.log('[MYSQL ERROR]',err);
            
        });
        if (result && result.length)
            res.json('User already exists!!!');
        else {
            con.query('INSERT INTO `user`(`unique_id`, `name`, `lastName`, `email`, `phoneNumber`, `encrypted_password`, `salt`, `created_at`, `updated_at`)  ' +
                'VALUES (?,?,?,?,?,?,?,NOW(),NOW())',[uid,name,lastName,email,phoneNumber,password,salt],function (err,result,fields) {
                if (err) throw err;

                res.json('successfully registered !');

            })
        }
    });

})


app.post('/addHotel/',(req,res,next)=>{
    
	var name = req.query.name;
	var adreese = req.query.adresse;
	var image = req.query.image;
	var star = req.query.star;
	var price = req.query.price;

     con.query('INSERT INTO `hotels`(`hotel_name`, `hotel_adresse`, `hotel_image`, `hotel_star`, `hotel_price`)  ' +
                'VALUES (?,?,?,?,?)',[name,adreese,image,star,price],function (err,result,fields) {
                if (err) throw err;

                res.json('successfully added !');

            });

})

app.get('/hotels/',(req,res,next)=>{
   

    con.query('SELECT * FROM hotels',function (err,result,fields) {
        con.on('error', function (err) {
            console.log('[MYSQL ERROR]', err);
        });
        if (result && result.length)

            res.end(JSON.stringify(result))


    });


})

app.post('/addRestaurant/',(req,res,next)=>{
    
	var name = req.query.name;
	var adresse = req.query.adresse;
	var image = req.query.image;
	var star = req.query.star;
	
     con.query('INSERT INTO `restaurants`(`resto_name`, `resto_adresse`, `resto_image`, `resto_star`)  ' +
                'VALUES (?,?,?,?)',[name,adresse,image,star],function (err,result,fields) {
                if (err) throw err;

                res.json('successfully added !');

            });

})

app.get('/restaurants/',(req,res,next)=>{
   

    con.query('SELECT * FROM restaurants',function (err,result,fields) {
        con.on('error', function (err) {
            console.log('[MYSQL ERROR]', err);
        });
        if (result && result.length)

            res.end(JSON.stringify(result))

    });
})


app.post('/addActivity/',(req,res,next)=>{
    
	var name = req.query.name;
	var adreese = req.query.adresse;
	var image = req.query.image;
	var price = req.query.price;
	

     con.query('INSERT INTO `activities`(`activity_name`, `activity_adresse`, `activity_image`, `activity_price`)  ' +
                'VALUES (?,?,?,?)',[name,adreese,image,price],function (err,result,fields) {
                if (err) throw err;

                res.json('successfully added !');

            });

})


app.get('/activities/',(req,res,next)=>{
   

    con.query('SELECT * FROM activities',function (err,result,fields) {
        con.on('error', function (err) {
            console.log('[MYSQL ERROR]', err);
        });
        if (result && result.length)

            res.end(JSON.stringify(result))


    });


})



/*app.get("/",(req,res,next) =>{
    console.log('Password: 123456');
    var encrypt = saltHashPassword("123456");
    console.log('Encrypt: '+encrypt.passwordHash);
    console.log('Salt: '+ encrypt.salt);

})*/

//Start Server
app.listen(1337,()=>{
    console.log('Restful Running on port 1337');
})