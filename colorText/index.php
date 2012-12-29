<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
	<head> 
		<title>Color Text</title>
		<link rel="stylesheet" type="text/css" href="styles/style.css" />
	</head>
	
	<body>
		<h1>Welcome to the ColorText page!</h1>
		<p>
			This lets you color text to look like an image.
			For example, below converts "A Raisin in the Sun" to Bart Simpson.
			<img src="images/sample.png" alt="sample" />
			<br /><br /><br /><br />
		</p>
		
		<div>
			<h3>Let's make an image!</h3>
			<form method="post" action="phpStuff.php" enctype="multipart/form-data">
				<label>Please enter your name:</label>
					<input type="text" name="person_name" />
				<br />
				<label>Choose the image you want (.png only)<sup>(1)</sup>:</label>
					<input type="file" name="imageFile" />
				<br />
				<label>Choose a background color (hex only)<sup>(2)</sup>:</label>
					<input type="text" name="backgroundColor" />
				<br />
				<label>Please choose a width/height value for a character<sup>(3)</sup>:</label>
					<input type="text" name="widthHeight" />
				<br />
				<label>Choose the text you want to use to generate the image<sup>(4)</sup>:</label>
				<br />
	  				<input type="text" name="essay" rows="20" cols="30" />
				<br />
				<label>Please choose the font you would like to use:</label>
					<select name="fontChoice">
						<option value="Courier">Courier</option>
						<option value="Courier Std">Courier Std</option>
						<option value="Lucida Console">Lucida Console</option>
						<option value="Lucida Sans Typewriter">Lucida Sans Typewriter</option>
						<option value="Monaco">Monaco</option>
						<option value="Andale Mono">Andale Mono</option>
						<option value="Terminal">Terminal</option>
					</select>
				<br />
				<input type="submit" name="Create my image!" />
			</form>
			
			<p>
				<br/>
				Note:<br />
				(1) This program will not work with any image extension other than '.png'. The image you select must be placed in the "images" folder of this project beforehand and chosen from there.<br />
				(2) The color chosen must be a hex value. You can use <a href="http://www.colorpicker.com">Color Picker</a> to choose a value<br />
				(3) An ideal choice for the pixel width is between 4 and 10 (smaller numbers lead to larger, more-detailed images and vice versa)<br />
				(4) You want a lot of text (a few thousand characters is good)			
			</p>
		</div>
		
	</body>
</html>