
/**
 * 分块计算文件的md5
 */
function doIncremental(running, file, fileObj) {
    //jquery延时函数
    var dfd = $.Deferred();

    if (running) {
        return;
    }

    /*if (!input.files.length) {
        registerLog('<strong>请选择一个文件</strong><br/>');
        return;
    }*/

    var blobSlice = File.prototype.slice || File.prototype.mozSlice || File.prototype.webkitSlice,
        //file = input.files[0],
        chunkSize = 2097152, // read in chunks of 2MB
        chunks = Math.ceil(file.size / chunkSize),
        currentChunk = 0,
        spark = new SparkMD5.ArrayBuffer(),
        time,
        uniqueId = 'chunk_' + (new Date().getTime()),
        chunkId = null,
        fileReader = new FileReader();

    fileReader.onload = function(e) {
        if (currentChunk === 0) {
            /*registerLog(
                    '读取分块<strong id="' + uniqueId + '">'
                            + (currentChunk + 1)
                            + '</strong> of <strong>' + chunks
                            + '</strong><br/>', 'info');*/
        } else {
            if (chunkId === null) {
                chunkId = document.getElementById(uniqueId);
            }

            //chunkId.innerHTML = currentChunk + 1;
        }

        spark.append(e.target.result); // append array buffer
        currentChunk += 1;

        if (currentChunk < chunks) {
            loadNext();
        } else {
            running = false;
            /*registerLog('<strong>载入完成！</strong><br/>', 'success');
            registerLog('<strong>计算出的哈希：</strong> ' + spark.end() + '<br/>', 'success'); // compute hash
            registerLog('<strong>耗时：</strong> ' + (new Date().getTime() - time) + 'ms<br/>', 'success');*/
            //返回成功计算出的哈希
            fileObj.fileHash = spark.end();
            dfd.resolve();
        }
    };

    fileReader.onerror = function() {
        running = false;
        registerLog('<strong>Oops, something went wrong.</strong>', 'error');
        dfd.resolve();
    };

    function loadNext() {
        var start = currentChunk * chunkSize, end = start + chunkSize >= file.size ? file.size : start + chunkSize;

        fileReader.readAsArrayBuffer(blobSlice.call(file, start, end));
    }

    running = true;
    /*registerLog('<p></p><strong>开始增量测试：(' + file.name
            + ')</strong><br/>', 'info');*/
    time = new Date().getTime();
    loadNext();

    //返回不可改状态对象
    return dfd.promise();
}