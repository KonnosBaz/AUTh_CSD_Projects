##Implementation of the BisectionMethod with a loop counter
##Input : 
##		-f : an anonymous function
##		-[a,b] : an interval with at least one root in it
##Output :
##		-root : an approximation of the root (within the acceptable error if possible)
##		-loops : a count of the loops needed to reach the approximation
##Notes :
##		-The function tries to make sure that a root exists in [a,b] by checking that f(a) and f(b) are on different sides of the x-axis
##		-The function is intended for use with intervals that contain only one root and could fail if f is not continuous in [a,b]
##		-The function won't fail if a or b are roots but no usefull information will be gained
##		-If the function can't calculate a root within the desired acuracy, it returns the bestt approcimation so far and informs the user


function [root,loops] = BisectionMethod (f,a,b)
	##Declaration of acceptable error
	er = 0.000001 ;
		
	##Initialize loop counter
	loops = 0 ;
	
	##Check if a or b are roots
	if (f(a)==0)
		printf("Root found at a = %f \n",a)
		root = a
		return
	endif
	
	if (f(b)==0)
		printf("Root found at b = %f \n",b)
		root = b
		return
	endif
	
	##Confirm that a root exists in [a,b]
	if (f(a)*f(b)>0)
		error ("f(a) and f(b) should be on opposite sides of the x-axis")
		return 
	endif
	
	
	while (true)
		##Increment loop counter
		loops ++;
		
		##Calculate midpoint
		c = (a+b)/2;

		##printf("%.15f\n",c);

		##Check if the midpoint is an acceptable approximation of the root
		if (abs(b-a)<=er*abs(b))
			root = c
		  loops
			return
		##Or if the calculation is stuck in an infinite loop
		elseif (c==a || c==b)
			disp ("This approximation is NOT within the acceptable error, but no further progress can be made")
			root = c
			loops
			return
		endif

		##If none of the above conditions are met then redefine the interval [a,b]
		if (f(a)*f(c)<0)
			b=c;
		else
			a=c;
		endif
		
		##And repeat
	endwhile


	
endfunction
