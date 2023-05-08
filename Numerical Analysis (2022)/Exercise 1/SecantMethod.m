function [root,loops] = SecantMethod (f,x0,x1)
	##Define Acceptable Error	
	er = 0.00001;
	##Initialize Loop Counter
	loops = 0;

	for i = 1:100
		##Increment loop counter
		loops ++;		

		##Calculate new approximation
		x2 = x1 - f(x1)/((f(x1) - f(x0))/(x1-x0));
		
		##printf("%.20f\n",x2);

		##Check if approximation is within the acceptable error
		if(abs(x2 - x1)<=er*abs(x2))
			root = x2
			loops
			return
		endif
		##Update Values
		x0 = x1;
		x1 = x2;
	##Repeat
	endfor

endfunction
