module("SiteMesh");

test("Can extract title", function() {
    expect(1);
    stop();
    jQuery.get("/test.html?decorator=title", {}, function(data) {
        equals(data, "Test");
        start();
    });
});

test("Can extract div", function() {
    expect(1);
    stop();
    jQuery.get("/anotherfile.html?decorator=div&id=target", {}, function(data) {
        equals(data, "But we can see this");
        start();
    });
});

test("Can extract two divs", function() {
    expect(1);
    stop();
    jQuery.get("/anotherfile.html?decorator=div&id=target2&id=target", {}, function(data) {
        equals(data, "And another oneBut we can see this");
        start();
    });
});
