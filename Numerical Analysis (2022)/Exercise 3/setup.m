for	i = 1:10
	for j = 1:10
		if(i == j)
			A(i,j)=5;
		elseif (i+1 == j || j+1 == i)
			A(i,j)=-2;
		else
			A(i,j)=0;
		endif	
	endfor
endfor

b = ones(10,1);
b(1)=3;
b(10)=3;
