<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
	<head> 
		<title>Color Text</title>
		<link rel="stylesheet" type="text/css" href="styles/style.css" />
	</head>
	
	<body>
		
		<?php 
			$allFilled =  (isset($_POST["person_name"]) && isset($_FILES["imageFile"]["name"]) && isset($_POST["backgroundColor"]) && isset($_POST["essay"]) && isset($_POST["fontChoice"]) && isset($_POST["widthHeight"])
			        		  && strlen($_POST["person_name"]) > 0
					          && strlen($_FILES["imageFile"]["name"]) > 0
					          && strlen($_POST["backgroundColor"]) > 0
					          && strlen($_POST["essay"]) > 0
					          && strlen($_POST["fontChoice"]) > 0
					          && strlen($_POST["widthHeight"]) > 0
							  && is_numeric($_POST["widthHeight"])
							  && substr($_FILES["imageFile"]["name"],-3) == "png");
			
			
			if ($allFilled) {
				
				echo "<h1>Let's make an image!</h1>";
				
				$personName = $_POST["person_name"];
				$imageFile = $_FILES["imageFile"];
				$imageFileName = "images/" . $_FILES["imageFile"]["name"];
				$bckgrndClr = $_POST["backgroundColor"];
				$essayInput = $_POST["essay"];
				$font = $_POST["fontChoice"];
				$charSpace = $_POST["widthHeight"];
				
				echo "<p>";
				echo "<strong>Here are the values you submitted:</strong><br />";
				echo "<strong>* Your name:</strong> $personName<br />";
				echo "<strong>* Image:</strong> $imageFileName<br />";
				echo "<strong>* Background Color:</strong> $bckgrndClr<br />";
				echo "<strong>* Text:</strong> (not shown because it would take up too  much space)<br />";
				echo "<strong>* Font:</strong> $font<br />";
				echo "<strong>* Character size:</strong> $charSpace<br />";
				echo "<strong>* Here is the original image you selected:</strong><br /><img src='images/" . $_FILES["imageFile"]["name"] . "' alt='image' /><br />";
				echo "</p>";
				
				echo "
				<form method='post' action='index.php'>
				<label>If the information above is incorrect, please try again:</label>
				<input type='submit' value='Try Again' />";
				
				if (substr($bckgrndClr,0,1) != "#") {
					$bckgrndClr = "#" . $bckgrndClr; 
				}
				
				
				$nameArr = explode(" ", $personName);
				$newString = "";
				foreach ($nameArr as $word) {
					$newString .= ucfirst($word);
				}
				$personName = $newString;

				echo "<h2>It may take a moment to generate your image</h2>";
				getColorsFromPic($essayInput, $charSpace, $imageFileName, $personName, $bckgrndClr, $font);
				
			} else {
				echo "
					<h1>You need to fill in all values</h1>
					<form method='post' action='index.php'>
						<label>Please return to the home page to try again:</label>
						<input type='submit' value='Go to homepage' />
					</form>";
				
			}
			
			
			// takes in a text file and creates one continuous string where every word has the first letter capitalized
			function readThisFile() {
				$file_handle = fopen("test", 'r') or die("can't open file");
				$story = "";
				$arr = array();
			
				while(!feof($file_handle)) {
					$line = fgets($file_handle);
					$story .= $line;
					$arr[] = $line;
				}
				
				$storyArr = explode("\n", $story);
				$story = "";
				foreach ($storyArr as $line) {
					$story .= $line . " ";
				}
				
				$storyArr = explode(" ", $story);
				$newString = "";
				foreach ($storyArr as $word) {
					$newString .= ucfirst($word);
				}
				
// 				echo "<br />";
// 				echo $newString;
				
				return $newString;
			
				fclose($file_handle);
			}
			
			function colorString($inputStr, $numCharsX, $numCharsY, $matrix, $charWidth, $personName, $backgroundColor, $font) {
				$padding = 20;
				$width = $numCharsX*$charWidth+$padding;
				print '<font face="' . $font . '" size="1">';
				print "\n\n<p style='background-color: $backgroundColor ; padding:" . $padding . "px;'>\n";
				$strArr = str_split($inputStr); // get each individual character separately
				$numCharsThisLine = 0;
				$nameLength = strlen($personName);
				$lineNum = 0;
				foreach ($strArr as $char) {
					$color = $matrix[$lineNum][$numCharsThisLine];
					if ($lineNum == 0 && $numCharsThisLine < $nameLength) {
						print ("\t<span style='color:#ffffff'>$char</span>");
					} else {
						print ("\t<span style='color:" . $color . "' class='" . substr($color,1) . "'>$char</span>");
					}
					$numCharsThisLine++;
					if ($numCharsThisLine == $numCharsX) {
						$numCharsThisLine = 0;
						$lineNum++;
						print "\n\t<br />\n";
					}
				}
				print "\n</p>";
				print '</font>';
			}
			
			
			function getColorsFromPic($str, $charSize, $imgName, $personName, $backgroundColor, $font) {
				$str = $personName . $str;
				
				$storyArr = explode("\n", $str);
				$str = "";
				foreach ($storyArr as $line) {
					$str .= $line . " ";
				}
				
				$storyArr = explode(" ", $str);
				$newString = "";
				foreach ($storyArr as $word) {
					$newString .= ucfirst($word);
				}
				$str = $newString;
				
				$chars = str_split($str);
				
				$numChars = count($chars);
				
				$img = file_get_contents($imgName);
				
				$imgSize = getimagesize($imgName);
				
				$im = ImageCreateFromPng($imgName);
				
				$imgRatioX = 1;
				$imgRatioY = $imgSize[1]/$imgSize[0];
				
				$charWidth = $charSize;
				$charHeight = $charSize;
				$numCharsWidth = round($imgSize[0]/$charWidth); 
				$numCharsHeight = round($imgSize[1]/$charHeight);

				// go through the text and remove punctuation (. , - _) but leave ! ? etc.
				$str = "";
				$notOk = array(".", ",", "-", "_", "'", '"');
				foreach ($chars as $c) {
					if (!in_array($c, $notOk)) {
						$str .= $c;
					}
				}
				
				// add $strRep until our string is long enough
				$strRep = "@"; // the string to repeate until we have the desired length
				while (strlen($str) < ($numCharsWidth*$numCharsHeight)) {
					$str .= $strRep;
				}
				
				// if the string is too long, cut it down
				if (strlen($str) > ($numCharsWidth*$numCharsHeight)) {
					$str = substr($str, 0, ($numCharsWidth*$numCharsHeight));
				}
				
				
				// get the colors
				$matrix = array();
				for ($i = 0; $i < $numCharsHeight; $i++) {
					$matrix[$i] = array();
				}
				
				$clrSqX = round($imgSize[0]/$numCharsWidth); // number of pixels in width of one char
				$clrSqY = round($imgSize[1]/$numCharsHeight);
				$numSq = $clrSqX*$clrSqY;
				
				
				// go through each square
				for ($y = 0; $y < $numCharsHeight; $y++) {
					for ($x = 0; $x < $numCharsWidth; $x++) {
						$rgbVals = array();
						
						// go through all the pixels in that square
						for ($pX = 0; $pX < $charWidth; $pX++) {
							for ($pY = 0; $pY < $charHeight; $pY++) {
								$rgb = imagecolorat($im, $x*$charWidth+$pX, $y*$charHeight+$pY);
								$r = ($rgb >> 16) & 0xFF;
								$g = ($rgb >> 8) & 0xFF;
								$b = $rgb & 0xFF;
								
								$rgbVals[0] += $r;
								$rgbVals[1] += $g;
								$rgbVals[2] += $b;
								
							}
						}

						// get RGB values
						$rgbVals[0] = round($rgbVals[0]/$numSq);
						$rgbVals[1] = round($rgbVals[1]/$numSq);
						$rgbVals[2] = round($rgbVals[2]/$numSq);
						
						// convert to hex version
						$hexVal = rgb2html($rgbVals[0],$rgbVals[1],$rgbVals[2]);//hexdec($rgbStr);//[0], $rgbVals[1], $rgbVals[2]);
						
						// add hex color to the matrix
						$matrix[$y][] = $hexVal;
					}
				}
				
				// call colorString() to write the code to output the formatted html
				colorString($str, $numCharsWidth, $numCharsHeight, $matrix, $charWidth, $personName, $backgroundColor, $font);
				
				// write results to a file so that we can get it in the css
				// get the unique colors only so we don't re-write the same colors again and again
				$uniqueColors = array();
				for ($x = 0; $x < $numCharsWidth; $x++) {
					for ($y = 0; $y < $numCharsHeight; $y++) {
						if (!in_array($matrix[$y][$x], $uniqueColors)) { // if the array is not in the array, add it
							$uniqueColors[] = $matrix[$y][$x];
						}
					}
				}
				
			}
			
			
			// converts rgb to hex
			function rgb2html($r, $g, $b)
			{
				if (is_array($r) && sizeof($r) == 3)
					list($r, $g, $b) = $r;
			
				$r = intval($r); $g = intval($g);
				$b = intval($b);
			
				$r = dechex($r<0?0:($r>255?255:$r));
				$g = dechex($g<0?0:($g>255?255:$g));
				$b = dechex($b<0?0:($b>255?255:$b));
			
				$color = (strlen($r) < 2?'0':'').$r;
				$color .= (strlen($g) < 2?'0':'').$g;
				$color .= (strlen($b) < 2?'0':'').$b;
				return '#'.$color;
			}
			
		?>
		
		<div>
			<br />
			<h1>Want to create another?</h1>
			<form method="post" action="index.php">
				<label></label>
				<input type="submit" value="Yes, create another!" />
			</form>
			<br /><br /><br />
		</div>
	
	</body>
</html>