$(document).ready(function($) {
			$('#accordion').find('.accordion-toggle').click(function(){

				//Expand or collapse this panel
				$(this).next().slideToggle('fast');
				$(this).toggleClass('on');

				//Hide the other panels
				//$(".accordion-content").not($(this).next()).slideUp('fast');

			});
		});
		
		$('#table').click(function(){
			$(this).toggleClass('on');
		});