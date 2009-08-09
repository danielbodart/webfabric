module("Cache Filter");

test("Sets Cache-Control max-age and Expires to 1 minute and on cacheable content", function() {
    expect(4);
    stop();
    jQuery.ajax({
      type: "GET",
      url: "/test/abc.txt",
      complete: function(req, textStatus){
          var expectedSeconds = 60;
          equals(req.getResponseHeader('Cache-Control'), 'public, max-age=' + expectedSeconds, "Cache-Control");
          var date = new Date(req.getResponseHeader('Date'));
          ok(!isNaN(date), "Date: " + date);
          var expires = new Date(req.getResponseHeader('Expires'));
          ok(!isNaN(expires), "Expires: " + expires);
          equals(expires - date, expectedSeconds * 1000, "Expires - Date (in milliseconds)");
          start();
      }
    });
});

