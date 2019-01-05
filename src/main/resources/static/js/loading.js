window.addEventListener("load", function(){
  $.mobile.navigate("#loadscreenPage");
  //sleep(5000);
  $.mobile.navigate("#login");

  function sleep(milliseconds) {
    var start = new Date().getTime();
    for (var i = 0; i < 1e7; i++) {
      if ((new Date().getTime() - start) > milliseconds){
        break;
      }
    }
  }
});
