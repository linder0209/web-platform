
//node debug debug.js
var a = 1;
var b = 'world';
var c = function(x) {
    console.log('hello ' + x + a);
    console.log(process.argv);
};
c(b);