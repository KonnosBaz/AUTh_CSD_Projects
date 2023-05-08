

function retval = NewtonRaphson2 (f,x0)
	##Define acceptable error
	er = 0.000000001;

	##Define Derrivative Functions
	f1 = @(x) (f(x+0.00000000001) - f(x))/0.00000000001;
	f2 = @(x) (f1(x + 0.00000000001) - f1(x))/0.00000000001;

	##Initialize loop counter
	loops = 0;


	for i = 1:100
		
		##Increment loop counter
		loops++;

		#Calculate approximation
		x1 = x0 - 1/((f1(x0)/f(x0))-(f2(x0)/2*f1(x0)));
		
		printf("%.15f\n",x1);

		#Check for validity
		if (abs(x1-x0)<=er)
			root = x1
			loops
			return;
		endif
		
		x0 = x1;

	endfor
	
endfunction
