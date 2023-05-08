function x = GaussSiedel (A, b)
	n = size(A,1);
	x = zeros(n,1);

	for	l = 1:100
		
		xLast = x;
		for	i = 1:n
			S1=0;
			S2=0;
			for	j = 1:i-1
					S1 += A(i,j)*x(j);
			endfor
			for	j = i+1:n
					S2 += A(i,j)*x(j);
			endfor
			x(i) = (b(i) - S1 - S2)/A(i,i);
		endfor
		if (norm(x-xLast,inf)<= 0.00001)
			return;
		endif


	endfor

endfunction
