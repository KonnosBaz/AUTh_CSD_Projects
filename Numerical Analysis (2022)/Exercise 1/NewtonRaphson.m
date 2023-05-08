function [root,loops] = NewtonRaphson (f,x0)
	
	##Define the derivative of f (the specific value of dx is as small as we can go without causing problems in the evaluation)
	f1 = @(x) (f(x + 0.00000000001) - f(x))/0.00000000001;
	
	##Set acceptable error (for very small values the execution might not finish)
	er = 0.00001;

	##Initialize loop counter
	loops = 0;
	
	##z is the variable that holds the previous estimation to check for 2-value loops	
	z=-123456;

	##(Use of for loop to avoid infinite loops)
	for i = 1:1000
		
		##Increment loop counter
		loops ++;
	
		##Estimate Xn+1
		x1 = x0 - f(x0)/f1(x0);

		##printf("%.20f\n",x1);
		
		##If the estimation is good enough return the results
		if (abs(x1-x0)<=er*abs(x1))
			root = x1
			loops	
			return
		##Or if the process is stuck looping between 2 values
		elseif (x1 == z)
			disp("This approximation is NOT within the acceptable error, but no further progress can be made")
			root = x1
			loops
			return
		endif

		##Keep this loop's x0 and update it for the next loop
		z = x0;
		x0 = x1;
	
	##Repeat
	endfor

	
endfunction
