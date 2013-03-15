
/**
 * Module dependencies.
 */

var express = require('express')
        , routes = require('./routes')
        , user = require('./routes/user')
        , http = require('http')
        , path = require('path');

var app = express();

app.configure(function() {
    app.set('port', process.env.PORT || 3000);
    app.set('views', __dirname + '/views');
    app.set('view engine', 'jade');
    app.use(express.favicon());
    app.use(express.logger('dev'));
    app.use(express.bodyParser());
    app.use(express.methodOverride());
    app.use(app.router);
    app.use(express.static(path.join(__dirname, 'public')));
});

app.configure('development', function() {
    app.use(express.errorHandler());
});

app.get('/', routes.index);
app.get('/users', user.list);
app.get('/hello', routes.hello);

//Express 提供了路由控制权转移的方法，即回调函数的第三个参数next，通过调用
//next()，会将路由控制权转移给后面的规则
var users = {
    'byvoid': {
        name: 'Carbo',
        website: 'http://www.byvoid.com'
    }
};
app.all('/user/:username', function(req, res, next) {
    // 检查用户是否存在
    if (users[req.params.username]) {
        next();
    } else {
        next(new Error(req.params.username + ' does not exist.'));
    }
});
app.get('/user/:username', function(req, res) {
    // 用户一定存在，直接展示
    res.send(JSON.stringify(users[req.params.username]));
});
app.put('/user/:username', function(req, res) {
    // 修改用户信息
    res.send('Done');
});

http.createServer(app).listen(app.get('port'), function() {
    console.log("Express server listening on port " + app.get('port'));
});
