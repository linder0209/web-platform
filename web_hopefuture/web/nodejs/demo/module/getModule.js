//getmodule.js
//require 不会重复加载模块，也就是说无论调用多少次 require，获得的模块都是同一个
var hello1 = require('./module');
hello1.setName('BYVoid');
var hello2 = require('./module');
hello2.setName('BYVoid 2');
hello1.sayHello();