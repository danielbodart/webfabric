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
    expect(3);
    stop();
    jQuery.ajax({
      type: "GET",
      url: "/test/abc.txt",
      complete: function(req, textStatus){
          var date = new Date(req.getResponseHeader('Date'));
          ok(!isNaN(date), "Date: " + date)
          var lastModified = new Date(req.getResponseHeader('Last-Modified'));
          ok(!isNaN(lastModified), "Last-Modified: " + lastModified)
          ok(lastModified <= date, "Last-Modified =< Date")          
          start();
      }
    });
});
