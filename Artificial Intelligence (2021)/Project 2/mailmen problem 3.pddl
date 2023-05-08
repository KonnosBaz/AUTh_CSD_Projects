(define (problem mailmen3)
  (:domain mailmen)

  (:objects p1 p2 p3 p4 p5 p6 p7 p8 p9 p10 p11 p12 p13 p14 p15 m1 m2 l1 l2 l3)

  
  (:init
    ;defining points
    (point p1)(point p2)(point p3)(point p4)(point p5)
    (point p6)(point p7)(point p8)(point p9)(point p10)
    (point p11)(point p12)(point p13)(point p14)(point p15)
    ;defining mailmen
    (mailman m1)
    (mailman m2)
    ;defining letters
    (letter l1)
    (letter l2)
    (letter l3)
    ;specifying letters and mailman position
    (at m1 p3)
    (at m2 p3)
    (at l1 p4)
    (at l2 p9)
    (at l3 p14)
    ;specifying mailbox positions
    (hasMailbox p7)
    (hasMailbox p15)
    
    ;defining roads
    (connection p5 p7)(connection p7 p5)
    (connection p2 p8)(connection p8 p2)
    (connection p8 p9)(connection p9 p8)
    (connection p9 p10)(connection p10 p9)
    (connection p12 p14)(connection p14 p12)
    (connection p13 p15)(connection p15 p13)
    ;defining metros
    (connection p1 p2)
    (connection p2 p3)
    (connection p3 p1)
    
    (connection p1 p4)
    (connection p4 p5)
    (connection p5 p6)
    (connection p6 p1)
    
    (connection p10 p13)
    (connection p13 p12)
    (connection p12 p11)
    (connection p11 p10)
  )

  
  (:goal 
    (and
        (delivered l1)
        (delivered l2)
        (delivered l3)
    )
  )
)