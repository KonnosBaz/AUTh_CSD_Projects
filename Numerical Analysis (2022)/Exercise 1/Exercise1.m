f = func1;

fplot(f,[0,3]);

legend("f(x) = 14xe^{(x-2)} - 12e^{(x-2)} - 7x^3 + 20x^2 - 26x + 12")
xlabel("x")
ylabel("f(x)")
grid("on")


printf("As is we can see in the plot, f has two roots, one close to 0.85 and one on 2\n")


[r,l] = BisectionMethod(f,0,1);
printf("The Bisection Method found the first root at %.5f in %d loops (given the intrval [0,1])\n",r,l)

[r,l] = BisectionMethod(f,1,3);
printf("and the second root at %.5f in %d loops (given the interval [1,3])\n",r,l)


[r,l] = NewtonRaphson(f,1);
printf("The Newton-Raphson Method found the first root at %.5f in %d loops (given starting point 1)\n",r,l)

printf("But is unable to find the second root\n")

[r,l] = SecantMethod(f,0,1);
printf("The Secant Method found the first root at %.5f in %d loops (given 0 and 1 as starting points)\n",r,l)
printf("But is unable to find the second root")