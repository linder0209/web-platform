var exec = require("child_process").exec;

function sleep(milliSeconds) {
    var startTime = new Date().getTime();
    while (new Date().getTime() < startTime + milliSeconds) {

    }
}
function start(response) {
    console.log("Request handler 'start' was called.");
    exec("ls-lah", {timeout: 10000, maxBuffer: 20000 * 1024}, function(error, stdout, stderr) {
        response.writeHead(200, {"Content-Type": "text/plain"});
        response.write("结果：" + stdout);
        response.end();
    });
}
function upload(response) {
    console.log("Request handler 'upload' was called.");
    response.writeHead(200, {"Content-Type": "text/plain"});
    response.write("Hello Upload");
    response.end();
}
exports.start = start;
exports.upload = upload;