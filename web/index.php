<?php
$unitNames = array("Structure", "Bonding", "Naming Rxns", "Organic Molecules", "Moles", "Mass Stoich.", "Solutions", "Solubility", "Acids", "Gas Laws", "Gas Stoich.");
$chapter = $_GET["c"];
?>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Thousands of multiple choice questions for you to test your grade 11 chemistry skills on.">

<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-63899316-1', 'auto');
  ga('send', 'pageview');

</script>

<script>

var currentQuestion = {
    'chapter':0,
    'values':[], 
    'correct':"a",
    'question':"nothing",
    'id': 0,
    'fulltext':null
};
var charArray = ['A', 'B', 'C', 'D', 'E'];
var customQuestion = -1;
var correct = 0, total = 0;
var jokes = null;

function startTimer(duration, display) {
    var timer = duration, minutes, seconds;
    setInterval(function () {
        minutes = parseInt(timer / 60, 10);
        seconds = parseInt(timer % 60, 10);

        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

        display.innerHTML = minutes + ":" + seconds + " until you've done your 15 minutes";

        if (--timer < 0) {
            timer = duration;
	    alert("You've done 15 minutes. Good job. You can continue to study, or get off. Either way, Mr. Greisman is happy.");
        }
    }, 1000);
}

window.onload = function () {
    var fiveMinutes = 60 * 15, display = document.getElementById('timer_label');
    startTimer(fiveMinutes, display);
};

function readTextFile(file){
    var rawFile = new XMLHttpRequest();
    rawFile.open("GET", file, false);
    rawFile.onreadystatechange = function(){
        if(rawFile.readyState === 4){
            if(rawFile.status === 200 || rawFile.status == 0){
                var allText = rawFile.responseText;
                console.log("Got file: " + file);
                currentQuestion.fulltext = allText;
                loadNewQuestion();
            }
        }
    }
    rawFile.send(null);
}

function readJokes(){
    var file = "jokes.txt";
    var rawFile = new XMLHttpRequest();
    rawFile.open("GET", file, false);
    rawFile.onreadystatechange = function(){
        if(rawFile.readyState === 4){
            if(rawFile.status === 200 || rawFile.status == 0){
                var allText = rawFile.responseText;
                jokes = allText.split("- - -");
                updateJoke();
            }
        }
    }
    rawFile.send(null);
}

function updateJoke(){
    var num = Math.floor(Math.random() * jokes.length);
    var joke = jokes[num];

    document.getElementById("subtitle").innerHTML = joke;
}

function reloadHTML(){
    document.getElementById("question_label").innerHTML = currentQuestion.question;
    for(var i = 2; i < 7; i++){
        var option = "l_option-" + charArray[i-2].toLowerCase();
        if(currentQuestion.values[i] !== "null"){
            document.getElementById(option).style.visibility = "visible";
            document.getElementById(option).innerHTML = '<input id="option-' + charArray[i-2].toLowerCase() + '" type="radio" name="s" value="' + charArray[i-2] + '">  ' + currentQuestion.values[i];
        }
        else{
            document.getElementById(option).style.visibility = "hidden";
        }
    }
    if(currentQuestion.question.substring(currentQuestion.question.length-4) === ".png"){
        if(document.getElementById("imagesenabled").checked){
            document.getElementById("imageview").src = currentQuestion.question;
            document.getElementById("imageview").style.visibility = "visible";
        }
        else{
            loadNewQuestion();
        }
    }
    else{
        document.getElementById("imagefix").innerHTML = '<img src="" id="imageview" style="hidden;"></img>';
    }
}

function newQuestionButtonHit(){
    document.getElementById("status_label").innerHTML = "";
    readTextFile("c" + parse("c") + ".txt");
}

function loadNewQuestion(){
    document.getElementById("guess_button").disabled = false;
    var array = currentQuestion.fulltext.split("---");
    var number = Math.floor((Math.random() * array.length-1));
    if(customQuestion > -1){
        number = customQuestion;
        customQuestion = -1;
        console.log("using " + array[number]);

    }
    console.log("full text " + array.length);
    var newQuestion = array[number].split("\n");
    if(newQuestion[7] === undefined || newQuestion[7] === null){
        newQuestionButtonHit();
        return;
    }
    currentQuestion.id = number;
    currentQuestion.correct = newQuestion[1];
    currentQuestion.fulltext = array[number];
    currentQuestion.question = newQuestion[7];
    currentQuestion.chapter = parse("c");
    for (var i = 6; i >= 2; i--) {
        currentQuestion.values[i] = newQuestion[i];
    };
    reloadHTML();
}

function parse(val) {
    var result = "not found",
       tmp = [];
    location.search
    //.replace ( "?", "" ) 
    // this is better, there might be a question mark inside
    .substr(1)
       .split("&")
        .forEach(function (item) {
    tmp = item.split("=");
    if (tmp[0] === val) result = decodeURIComponent(tmp[1]);
    });
    return result;
}

function getRadioCheckedValue(radio_name){
   var oRadio = document.forms[0].elements[radio_name];
 
   for(var i = 0; i < oRadio.length; i++){
      if(oRadio[i].checked){
         return oRadio[i].value;
      }
   }
 
   return '';
}

function disable_elements(){
    for(var i = 2; i < 7; i++){
        var option = "option-" + charArray[i-2].toLowerCase();
        var domoption = document.getElementById(option);
        if(domoption != null){
            if(currentQuestion.values[i] !== "null"){
                domoption.disabled = true;
            }
            else{
                domoption.style.visibility = "hidden";
            }
        }
        document.getElementById("guess_button").disabled = true;
    }
}

Element.prototype.remove = function() {
    this.parentElement.removeChild(this);
}
NodeList.prototype.remove = HTMLCollection.prototype.remove = function() {
    for(var i = 0, len = this.length; i < len; i++) {
        if(this[i] && this[i].parentElement) {
            this[i].parentElement.removeChild(this[i]);
        }
    }
}

var i = 0;
function checkQuestion(){

    if (window.File && window.FileReader && window.FileList && window.Blob) {
      if(getRadioCheckedValue("s") === currentQuestion.correct){
        document.getElementById("status_label").innerHTML = "Correct. Awesome work!";
        correct++;
        updateJoke();
        if(document.getElementById("soundsenabled").checked){
            var audio = new Audio('correct.mp3');
            audio.play();
        }
      }
      else{
        document.getElementById("status_label").innerHTML = "Sorry, that's wrong. The correct answer was " + currentQuestion.correct + ".";
      }
      total++;
      disable_elements();

      document.getElementById("new_question_button").style.visibility = "visible";
      document.getElementById("score").innerHTML = "Score: " + correct + "/" + total + " (" + ((correct/total)*100).toFixed(2) + "%)"
      
      //loadNewQuestion(null);
      //alert("current question: " + JSON.stringify(currentQuestion));
    } else {
      alert('This browser won\'t work with chemistry tester. Please use a different/newer browser!');
    }

    i++;
        var element = document.getElementById("status_label");

    return false;
}

function showContent(){
    var randomNum = Math.floor(Math.random() * 11);

    document.getElementById("destroy_me").remove();
    document.getElementById("main_intro").remove();
    document.getElementById("footer").remove();
    document.getElementById("main").style.visibility = "visible";
    return false;
}

function newChapter(){
    var newChap = Math.floor(Math.random() * 11) + 1;
    window.location.href = "https://chemistry.edwinfinch.com/?c=" + newChap;
    showContent();
    return false;
}

function shareQuestion(){
    var question = currentQuestion.id;
    var chapter = currentQuestion.chapter;
    var url = "https://chemistry.edwinfinch.com/?c=" + chapter + "&q=" + question;

    window.prompt("Copy this text and share with who you want!", url);
}

function loadQuestionFromURL(){
    customQuestion = parse("q");
    console.log("custom qyestion: " + customQuestion);
}

</script>

    <title>SCH3U Multiple Guess Test</title>

<link rel="stylesheet" href="pure-min.css">
  
    <!--[if lte IE 8]>
        <link rel="stylesheet" href="css/layouts/side-menu-old-ie.css">
    <![endif]-->
    <!--[if gt IE 8]><!-->
        <link rel="stylesheet" href="css/layouts/side-menu.css">
    <!--<![endif]-->
</head>
<body>

<div id="layout">
    <!-- Menu toggle -->
    <a href="#menu" id="menuLink" class="menu-link">
        <!-- Hamburger icon -->
        <span></span> 
    </a>

    <div id="menu">
        <div class="pure-menu">
            <ul class="pure-menu-list">
   
		<?php
			for($i = 1; $i < 12; $i++){
                $name = $unitNames[$i];
				echo "<li class=\"pure-menu-item\"><a href=\"https://chemistry.edwinfinch.com/?c={$i}\" class=\"pure-menu-link\">{$name}</a></li>";
			}
		?>
		</ul>
        </div>
    </div>

    <p id="destroy_me">
    <div id="main_intro">
        <div class="header">
            <h1>SCH3U Multiple Guess Test</h1>
            <h2>Test your chemistry knowledge!</h2>
        </div>

        <div class="content">
            <h2 class="content-subhead" align="center">Get started</h2>
            <p>
    		To begin, click on a specific category in the sidebar located on the left, or click on the "get started" button below, which will
            bring you to a random chapter. There are 11 chapters, all with varying questions.<br><br>
            </p>
    		<div align="center">
    			<form onsubmit="return newChapter();">
    				<button class="pure-button pure-button-primary" action="submit">Random Chapter</button>
    			</form>
    		</div>
            <br>
            <h2 class="content-subhead" align="center">Want this for your class?</h2>
            <p>
                If you want to set this multiple guesser up for your class, I'd be happy to set it up for you, provided you create
                the multiple choice questions. For inquries, <a href="mailto:contact@edwinfinch.com">email me.</a>
            </p>
        </div>
        </div>
        <div class="footer" align="center" id="footer">
            <hr>
            Created by Edwin Finch. Made with love, and open source. Contribute to it on <a href="https://github.com/edwinfinch/sch3u">Github</a>.
        </div>
</p>

    <div id="main" style="visibility: hidden;">
        <div class="header">
            <h1><?php echo $unitNames[$chapter] ?></h1>
            <h2 id="subtitle">Test your chem knowledge</h2>
        </div>

        <div class="content" align="center">
        
    <h2 id="status_label"></h2>   

    <h2 class="content-subhead" id="question_label">Loading... If this doesn't do anything, please <a href="/">refresh.</a></h2>
    <p id="imagefix">
        <img src="" id="imageview" style="hidden;"></img>
    </p>
            <p>
        <form id="mainForm" class="pure-form" onsubmit="return checkQuestion();">
            <label for="option-a" class="pure-radio" id="l_option-a">
                <input id="option-a" type="radio" name="s" value="A" checked>
                Created by Edwin Finch
            </label>
        <label for="option-b" class="pure-radio" id="l_option-b">
                        <input id="option-b" type="radio" name="s" value="B">
                        Hacked off of some thing Mr. Greisman gave me
                </label>
        <label for="option-c" class="pure-radio" id="l_option-c">
                        <input id="option-c" type="radio" name="s" value="C">
                        I hope you're having a good day. Sorry for the wait and/or broken site.<br>
			
                </label>
        <label for="option-d" class="pure-radio" id="l_option-d">
                        <input id="option-d" type="radio" name="s" value="D">
                        #sixseasonsandamovie
                </label>
        <label for="option-e" class="pure-radio" id="l_option-e">
                        <input id="option-e" type="radio" name="s" value="E">
            		There's always money in the banana stand!
                </label>
        <input id="chapter" name="c" value="<?php echo $chapter ?>" hidden>
        <button action="submit" class="pure-button pure-button-primary" id="guess_button">Guess</button>
        </form>
<br>
        <button onClick="shareQuestion();" class="pure-button" id="share_question_button">Share Question</button>  
        <button onClick="newQuestionButtonHit();" class="pure-button pure-button-primary" id="new_question_button">New Question</button>  
        <button onClick="newChapter();" class="pure-button" id="new_chapter_button">New Chapter</button>
        <div id="footer" class="footer">
            <hr>
            <p id="score">No score yet :(</p>
	    <p id="timer_label">15:00</p>
            <input type="checkbox" name="sounds" value="true" id="soundsenabled" checked="true"> Sound effects <br>
            <input type="checkbox" name="images" value="true" id="imagesenabled" checked="true"> Image questions
        </div>
</div>
            </p>
</div>
</div>
</div>
</div>

<script src="js/ui.js"></script>
<script>
    readJokes();
    if(parse("c") !== "not found"){ 
        showContent(); 
        if(parse("q") !== "not found"){
            loadQuestionFromURL();
        } 
    }
    newQuestionButtonHit();
</script>

</body>
</html>
