module("ETag Filter");

test("Sets ETag header to the MD5 digest of the content in hex encoding (md5sum)", function() {
    expect(1);
    stop();
    jQuery.ajax({
      type: "GET",
      url: "/test/abc.txt",
      complete: function(req, textStatus){
          equals(req.getResponseHeader('ETag'), '"900150983cd24fb0d6963f7d28e17f72"');
          start();
      }
    });
});

test("Sets Content-MD5 to the MD5 digest of the content in base64 encoding", function() {
    expect(1);
    stop();
    jQuery.ajax({
      type: "GET",
      url: "/test/abc.txt",
      complete: function(req, textStatus){
          equals(req.getResponseHeader('Content-MD5'), 'kAFQmDzST7DWlj99KOF/cg==');
          start();
      }
    });
});

test("Sets Last-Modified to server time", function() {
    expect(1);
    stop();
    jQuery.ajax({
      type: "GET",
      url: "/test/abc.txt",
      complete: function(req, textStatus){
          var lastModified = req.getResponseHeader('Last-Modified');
          var serverTime = new Date(lastModified);
          ok(!isNaN(serverTime), "Server time: " + serverTime)
          start();
      }
    });
});
