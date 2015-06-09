<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="A layout example with a side menu that hides on mobile, just like the Pure website.">


    <title>Responsive Side Menu &ndash; Layout Examples &ndash; Pure</title>

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
                                echo "<li class=\"pure-menu-item\"><a href=\"https://chemistry.edwinfinch.com/chapter.php?c={$i}\" class=\"pure-menu-link\">Chapter {$i}</a></li>";
                        }
                ?>
                </ul>
        </div>
    </div>

    <div id="main">
        <div class="header">
            <h1>Chapter <?php echo $chapter ?></h1>
            <h2>This code is outdated, please use the main site for the multiple choice test.</h2>
        </div>

        <div class="content" align="center">
        
	<h2 id="status_label"></h2>   

	<h2 class="content-subhead" id="question_label">Loading new question... Please wait.</h2>
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
                        Shout out to our chem class for being the best
                </label>
		<label for="option-d" class="pure-radio" id="l_option-d">
                        <input id="option-d" type="radio" name="s" value="D">
                        D
                </label>
		<label for="option-e" class="pure-radio" id="l_option-e">
                        <input id="option-e" type="radio" name="s" value="E">
			E
                </label>
		<input id="chapter" name="c" value="<?php echo $chapter ?>" hidden>
		<button action="submit" class="pure-button pure-button-primary">Guess</button>
	    </form>
<br>
        <button onClick="newQuestionButtonHit();" class="pure-button pure-button-primary" id="new_question_button">New Question</button> 

</div>
            </p>

    </div>
</div>





<script src="js/ui.js"></script>
<script>newQuestionButtonHit();</script>


</body>
</html>

