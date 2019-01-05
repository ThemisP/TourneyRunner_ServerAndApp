console.log("started");
var userinfo;
var host = "http://192.168.137.1:3000/";
$(document).ready(function(){userinfo = JSON.parse(getStoredValue("userinfo"));});

function listJoinedTournaments(){
  $("#tournamentList").empty();
  if(userinfo != null && userinfo != "undefined"){
    var joinedTournaments;
    var xhr = new XMLHttpRequest();
    xhr.open("GET", host + "users/" + userinfo.username + "/tournaments", true);
    xhr.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        joinedTournaments = JSON.parse(this.responseText);
        for(var index in joinedTournaments){
          $("#tournamentList").find(".ui-last-child").attr("class", "");
          $("#tournamentList").append('<li class=' + ((index == 0) ? "ui-first-child" : "ui-last-child") + '>' +
                                      '<a href="#tournamentView" onclick="tournamentCode.'+ ((joinedTournaments[index].pending) ? ('tournamentPending();') : ('startTournamentLoad(' + joinedTournaments[index].id  + ',\'' + joinedTournaments[index].name + '\');"')) + ' class="ui-btn ui-btn-icon-right ui-icon-carat-r">' +
                                      joinedTournaments[index].name, + '</a></li>');
        }
        $("#tournamentList").listview("refresh");
      }
    }
    xhr.send();
  } else {
    alert("Not logged in!");
  }
}

function showAccountDetails(){
  $("#accountDetails").empty();
  $("#accountDetails").append('<li>Username: ' + userinfo.username + '</li>' +
                                            '<li>Name: ' + userinfo.name + '</li>' +
                                            '<li>Surname: ' + userinfo.surname + '</li>' +
                                            '<li>E-mail: ' + userinfo.email + '</li>');
  $("#accountDetails").listview("refresh");
}

/////////////////////////////////////////////////////////////////
//                  Join Tournament
/////////////////////////////////////////////////////////////////
function joinTournament(){
  var code = $("#joinCode").val();
  if(!alreadyJoined(code)){
    var tournament;
    var xhr = new XMLHttpRequest();
    xhr.open("GET", host + "tournaments/?tournamentCode=" + code, true);
    xhr.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        tournament = JSON.parse(this.responseText)[0];
        $("#tournamentList").find(".ui-last-child").attr("class", "");
        $("#tournamentList").append('<li class="ui-last-child">' +
                                    '<a href="#tournamentView" onclick="tournamentCode.startTournamentLoad(\''+tournament.tournamentName+'\');" class="ui-btn ui-btn-icon-right ui-icon-carat-r">' +
                                    tournament.tournamentName + '</a></li>');
        userinfo.tournaments.push({name:tournament.tournamentName, code:tournament.tournamentCode})
        storeValue("userinfo", JSON.stringify(userinfo));
        updateUserTournaments();
      }
    }
    xhr.send();
  } else {
    console.log("Already joined");
  }

}

function alreadyJoined(code){
  if(userinfo != null && userinfo != "undefined"){
    for(var elem in userinfo.tournaments){
      if(userinfo.tournaments[elem].code == code){
        return true;
      }
    }
    return false;
  } else {
    alert("Not logged in!");
    return true;
  }
}

function updateUserTournaments(){
  var xhr1 = new XMLHttpRequest();
  xhr1.open("PUT", host + "users/"+userinfo.id, true);
  xhr1.setRequestHeader("Content-type", "application/json");
  xhr1.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      console.log("changed");
    }
  }
  xhr1.send(JSON.stringify(userinfo));
}
/*
Add a check if tournament trying to join
is already on the joined list
*/

/////////////////////////////////////////////////////////////////
//                  Join Tournament End
/////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////
//                  Login Retrieve data
/////////////////////////////////////////////////////////////////

var login = function(){

  var username = $("#loginUsername").val();
  var password = $("#loginPassword").val();
  var msg;
  var xhr = new XMLHttpRequest(); //this initializes object
  xhr.open("GET", host + "users/login/" + username + "/" + password, true);
  xhr.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      userinfo = JSON.parse(this.responseText);
      if(userinfo != null && userinfo != "undefined"){
          storeValue("userinfo",JSON.stringify(userinfo));
          $.mobile.navigate("#mainMenu");
          $("#loginUsername").val("");
          $("#loginPassword").val("");
      }
    }
  }
  xhr.send();
}

/////////////////////////////////////////////////////////////////
//                  Login Retrieve data End
/////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////
//                  Register End
/////////////////////////////////////////////////////////////////

var register = function(){
  var xhr = new XMLHttpRequest();
  xhr.open("GET", host + "users/" + $("#registerUsername").val(), true);
  xhr.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      if(this.responseText == ""){
        addRegisterUser();
      }
    }
  }
  xhr.send();
}

function addRegisterUser(){
  var data = {
    username: $("#registerUsername").val(),
    password: $("#registerPassword").val(),
    name: $("#registerName").val(),
    surname: $("#registerSurname").val(),
    email: $("#registerEmail").val()
  };
  var xhr1 = new XMLHttpRequest();
  xhr1.open("POST", host + "users/create", true);
  xhr1.setRequestHeader('Content-type','application/json; charset=utf-8');
  xhr1.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      userinfo = JSON.parse(this.responseText);
      $("#registerUsername").val("");
      $("#registerPassword").val("");
      $("#registerConfirmPassword").val("");
      $("#registerSurname").val("");
      $("#registerName").val("");
      $("#registerEmail").val("");
      $.mobile.navigate("#mainMenu");
    }
  }
  xhr1.send(JSON.stringify(data));
}

/////////////////////////////////////////////////////////////////
//                  Register End
/////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////
//                  Team View
/////////////////////////////////////////////////////////////////

function listJoinedTeams(){
  $("#teamList").empty();
  if(userinfo != null && userinfo != "undefined"){
    var joinedTeams;
    var xhr = new XMLHttpRequest();
    xhr.open("GET", host + "users/" + userinfo.username + "/teams", true);
    xhr.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        joinedTeams = JSON.parse(this.responseText);
        for(var index in joinedTeams){
          $("#teamList").find(".ui-last-child").attr("class", "");
          $("#teamList").append('<li class=' + ((index == 0) ? "ui-first-child" : "ui-last-child") + '>' + '<a class="ui-btn ui-btn-icon-right ui-icon-carat-r"' + 'onclick="teamLoader(\''+ joinedTeams[index].name +'\');"'
                                + '</a>' + joinedTeams[index].name  + '</li>');
        }
        $("#teamsList").listview("refresh");
      }
    }
    xhr.send();

  } else {
    alert("Not logged in!");
  }


}

function teamLoader(teamName){
  var team;
  var xhr = new XMLHttpRequest();
  xhr.open("GET", host + "teams/"+teamName,true);
  xhr.onreadystatechange = function() {
   if (this.readyState == 4 && this.status == 200) {
     team = JSON.parse(this.responseText);
     if(team== null || team == "undefined") alert("Something went wrongg!");
     else {
       $("#teamName").val(teamName);
       $.mobile.navigate("#teamViewPage");
       $("#teamsSpace").empty();
       for(var index in team.users){
           $("#teamsSpace").append('<li>' + team.users[index].username + '</li>');
       }
       $("#teamsSpace").listview("refresh");
     }
   }
  }

  xhr.send();
}
/////////////////////////////////////////////////////////////////
//                  Team View End
/////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////
//                  Create Tournament
/////////////////////////////////////////////////////////////////
var createTournament = {
  tournamentId:null,
  tournamentName:"",
  tournamentType:"",
  participants:[],
  create: function(){
    var sendTournament = {
      "name": $("#tournamentCreateName").val(),
      "admin": userinfo.username
    };
    var xhr = new XMLHttpRequest();
    xhr.open("POST", host + "tournaments/create", true);
    xhr.setRequestHeader("Content-type", "application/json")
    xhr.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        var tournament = JSON.parse(this.responseText);
        createTournament.tournamentId = tournament.id;
        createTournament.tournamentName = tournament.name;
        $("#tournamentAddParticipantsHeader").text(createTournament.tournamentName);
        $("#tournamentAddParticipantsHeader").trigger("refresh");
        $.mobile.navigate("#tournamentAddParticipantsPage");
        this.participants = [];
        $("#tournamentCreateParticipants").empty();
      }
    }
    xhr.send(JSON.stringify(sendTournament));
  },

  addParticipant: function(){
    var username = $("#tournamentCreateSearchParticipants").val();
    var xhr = new XMLHttpRequest(); //this initializes object
    xhr.open("GET", host + "users/" + username, true);
    xhr.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        $("#tournamentCreateSearchParticipants").val("");
        var user = JSON.parse(this.responseText);
        if(user != null && user != "undefined"){
          if(!createTournament.userAlreadyJoined(user.username)){
            createTournament.participants.push(user);
            $("#tournamentCreateParticipants").append('<li>' + user.username + '</li>');
            $("#tournamentCreateParticipants").listview("refresh");
          } else {
            console.log("user already joined");
          }
        } else {
          console.log("user not found");
        }
      }
    }
    xhr.send();
  },

  finalize: function(){
    if(this.participants.length == 0){

    } else {
      var xhr = new XMLHttpRequest(); //this initializes object
      xhr.open("PUT", host + "tournaments/" + this.tournamentId + "/finalize" , true);
      xhr.setRequestHeader("Content-type", "application/json")
      xhr.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
          $.mobile.navigate("#tournamentsPage");
        }
      }
      xhr.send(JSON.stringify(this.participants));
    }
  },

  delete: function(){
    var xhr = new XMLHttpRequest(); //this initializes object
    xhr.open("GET", host + "tournaments/delete/" + this.tournamentId, true);
    xhr.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
      }
    }
    xhr.send(JSON.stringify(this.participants));
    $.mobile.navigate("#mainMenu");
  },


  userAlreadyJoined: function(name){
    for(var index in this.participants){
      if(this.participants[index] === name) return true;
    }
    return false;
  }
}
/////////////////////////////////////////////////////////////////
//                  Create Tournament End
/////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////
//                  Tournament View
/////////////////////////////////////////////////////////////////


var tournamentCode = {
  stageCount:1,
  stageLimit:0,
  tournamentFixtures:null,
  tournamentName:"",
  tournamentId:null,
  fixture:null,

  //startTournamentLoad requests the corresponding tournament from server
  //stores it and executes appropriate functions to display on screen.
  startTournamentLoad: function(tournamentId, tournamentName){
    this.tournamentName = tournamentName;
    this.tournamentId = tournamentId;
    this.stageCount=1;
    var xhr = new XMLHttpRequest();
    xhr.open("GET", host + "tournaments/" + tournamentId + "/fixtures", true);
    xhr.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        tournamentCode.tournamentFixtures = JSON.parse(this.responseText);
        if(tournamentCode.tournamentFixtures == null || tournamentCode.tournamentFixtures == "undefined") alert("Something went wrong!");
        else {
          tournamentCode.setStageLim();
          tournamentCode.showTournament();
        }
      }
    }
    xhr.send();
  },

  //function for displaying the tournament (adding html inside the tag with id=tournamentSpace)
  showTournament: function(){
    $("#tournamentName").empty();
    $("#tournamentName").text(this.tournamentName);
    $("#tournamentSpace").empty();
    $("#tournamentSpace").append('<h1>Stage ' + this.stageCount + '</h1>');
    for(var elem in this.tournamentFixtures){
      var fixture = this.tournamentFixtures[elem];
      if(fixture.stage != null){
        if(fixture.stage == this.stageCount){
          $("#tournamentSpace").append('<ul data-role="listview" data-inset="true" class="ui-listview ui-listview-inset ui-corner-all ui-shadow">' +
                                      '<li class="ui-listview-item ui-listview-item-static ui-body-inherit ui-first-child">' + '<a class="ui-btn"' + 'onclick="tournamentCode.setScore(\''+ elem +'\');"' + '</a>' +
                                        ((fixture.player1 != null & fixture.player1!=undefined) ? fixture.player1 : "NaN") + '<span class="ui-li-count">'+ fixture.scorePlayer1 + '</span>' + '</li>' +
                                      '<li class="ui-listview-item ui-listview-item-static ui-body-inherit ui-last-child">' + '<a class="ui-btn"' + 'onclick="tournamentCode.setScore(\''+ elem +'\');"' + '</a>' +
                                        ((fixture.player2 != null & fixture.player2!=undefined) ? fixture.player2 : "NaN") + '<span class="ui-li-count">'+ fixture.scorePlayer2 +'</span>' + '</li>' + '</ul>');
        }
      }
    }
    $("#tournamentSpace").trigger("create");
  },

  setStageLim: function(){
    var max = 0;
    for(var elem in this.tournamentFixtures){
      var fixture = this.tournamentFixtures[elem];
      if(fixture.stage >max) max = fixture.stage;
    }
    this.stageLimit = max;
  },

  tournamentPending: function(){
    $("#tournamentName").empty();
    $("#tournamentName").text(this.tournamentName);
    $("#tournamentSpace").empty();
    $("#tournamentSpace").append('<h1>Tournement not finalised</h1>'+ '<p>&#9432; Participants can be added now, once all participants have gathered, the tournament can begin.</p>');
  },

  setScore: function(elem){
    var fixture = this.tournamentFixtures[elem];
    this.fixture = fixture;
    $("#player1Fixture").val(0).slider("refresh");
    $("#player2Fixture").val(0).slider("refresh");
    $("#player1NameClass").html(fixture.player1);
    $("#player2NameClass").html(fixture.player2);
    $("#player1NameClassRadio").html(fixture.player1);
    $("#player2NameClassRadio").html(fixture.player2);
    $("#scoreSetPopup").popup("open");
  },

  confirmScore: function(){
    if(this.fixture!=null){
      var player1Score = $("#player1FixtureScore").val();
      var player2Score = $("#player2FixtureScore").val();
      var winner = ($("input[name=radio-choice]:checked").val() == "choice-1") ? this.fixture.player1 : this.fixture.player2;
      this.fixture.scorePlayer1 = player1Score;
      this.fixture.scorePlayer2 = player2Score;
      this.fixture.winner = winner;
      var xhr = new XMLHttpRequest();
      xhr.open("PUT", host + "fixtures/score",true);
      xhr.setRequestHeader("Content-type", "application/json");
      xhr.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
          tournamentCode.startTournamentLoad(tournamentCode.tournamentId, tournamentCode.tournamentName);
        }
      }
      xhr.send(JSON.stringify(this.fixture));
      $("#scorePopupCloseButton").trigger("click");
    }
  }


}

/////////////////////////////////////////////////////////////////
//                  Tournament View End
/////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////
//                  Event Triggers/Listeners and cache
/////////////////////////////////////////////////////////////////
$(window).on( "swipeleft", function( event ) {
  var activePage = $.mobile.activePage.attr("id");
  if(activePage == "tournamentView"){ //handling of swipeleft on tournamentView
    if(tournamentCode.stageCount < tournamentCode.stageLimit) tournamentCode.stageCount+=1;
    tournamentCode.showTournament();
  }
} );


$(window).on( "swiperight", function( event ) {
  var activePage = $.mobile.activePage.attr("id");
  if(activePage == "tournamentView"){// handling of swipeRight on tournamentView
    if(tournamentCode.stageCount>1) tournamentCode.stageCount--;
      tournamentCode.showTournament();
  }
} );

$(document).on("pagecreate", "#tournamentsPage", function(){
  $( "#tournamentsPage" ).on( "pagebeforeshow", function(event, ui) {//add event listener for every time the tournamentsPage is shown.
    listJoinedTournaments();
  } );
});
$(document).on("pagecreate", "#accountDetailsPage", function(){
  $("#accountDetailsPage").on("pagebeforeshow", function(event, ui){
    showAccountDetails();
  });
});
$(document).on("pagecreate", "#teamsPage", function(){
  $("#teamsPage").on("pagebeforeshow", function(event, ui){
    listJoinedTeams();
  });
});
$(document).on("pagecreate", "#teamsViewPage", function(){
  $( "#teamsViewPage" ).on( "click", function(event, ui) {//add event listener for every time the teamsPage is shown.
    teamLoader();
  } );
});

function storeValue(key, value) {
    if (localStorage) {
        localStorage.setItem(key, value);
    } else {
        $.cookies.set(key, value);
    }
}
function getStoredValue(key) {
    if (localStorage) {
        return localStorage.getItem(key);
    } else {
        return $.cookies.get(key);
    }
}

/////////////////////////////////////////////////////////////////
//                  Event Triggers/Listeners End
/////////////////////////////////////////////////////////////////
console.log("loaded");























//End
