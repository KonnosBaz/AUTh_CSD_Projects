(define (problem mailmen2)
  (:domain mailmen)

  (:objects p1 p2 p3 p4 p5 p6 p7 p8 p9 p10 p11 m1 m2 l1 l2 l3)

  
  (:init
    ;defining points
    (point p1)(point p2)(point p3)(point p4)(point p5)
    (point p6)(point p7)(point p8)(point p9)(point p10)
    (point p11)
    ;defining mailmen
    (mailman m1)
    (mailman m2)
    ;defining letters
    (letter l1)
    (letter l2)
    (letter l3)
    ;specifying letters and mailman position
    (at m1 p1)
    (at m2 p5)
    (at l1 p3)
    (at l2 p8)
    (at l3 p11)
    ;specifying mailbox positions
    (hasMailbox p2)
    (hasMailbox p9)
    
    ;defining roads
    (connection p1 p2)(connection p2 p1)    
    (connection p1 p4)(connection p4 p1)
    (connection p3 p4)(connection p4 p3)
    (connection p4 p10)(connection p10 p4)
    (connection p5 p9)(connection p9 p5)
    (connection p5 p6)(connection p6 p5)
    (connection p7 p9)(connection p9 p7)
    (connection p10 p9)(connection p9 p10)
    (connection p10 p11)(connection p11 p10)
    ;defining metros
    (connection p1 p5)
    (connection p5 p4)
    (connection p4 p1)
    
    (connection p9 p4)
    (connection p4 p7)
    (connection p7 p8)
    (connection p8 p9)
  )

  
  (:goal 
    (and
        (delivered l1)
        (delivered l2)
        (delivered l3)
    )
  )
)