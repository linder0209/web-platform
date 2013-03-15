function Hello() {
    var name;
    this.setName = function(thyName) {
        name = thyName;
    };
    this.sayHello = function() {
        console.log('Hello ' + name);
    };
}
//require('./singleobject').Hello 来获取Hello 对象
exports.Hello = Hello;