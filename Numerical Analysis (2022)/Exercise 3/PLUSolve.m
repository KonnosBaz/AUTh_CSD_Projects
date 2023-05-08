function x = PLUSolve (A,b)
	
	if (!issquare(A))
		error("PLUSolve requires a square matrix as input");
		return;
	endif
	
	n = size(A,1);
	P = eye(n);
	

	##Get P by pivoting so every row's pivot has the highest possible mgnitude
	for	i = 1:n
	max = -Inf;
	maxrow = 0;
		##Find the row with the igest magnitude pivot (from the ones that haven't been used already)
		for j = i:n
			if(abs(A(j,i))>max)
				max = abs(A(j,i));
				maxrow = j;
			endif
		endfor
	##Get the corresponding permutation matrix, and use it on the original matrix as well as the overall permutation matrix
	Pt = rowExchange(n,i,maxrow);
	A = Pt*A;
	P = Pt*P;
	endfor
	
	L = eye(n);
	U = A;
	##Use the Gaussian algorithm to decompose into L and U, keeping track of L^-1
	for i = 1:n
		for	j = i+1:n
			Lt = rowAddition(n,j,(-U(j,i)/U(i,i)),i);
			L = Lt*L;
			U = Lt*U;		
		endfor
	endfor

	##Apply P and L^-1 on b
	b = P*b;
	b = L*b;

	##Ax = b
	##PAx = Pb
	##LUx = Pb
	##Ux = L^-1Pb
	
	##Use the backwards substitution algorithm
	for	i= n:-1:1
		q = 0;
		for j = i+1:n
			q+= U(i,j)*x(j,1);
		endfor
		x(i,1) = (b(i,1)-q)/U(i,i);
	endfor

##Done
endfunction
