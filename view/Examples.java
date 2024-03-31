package view;

import model.*;


public class Examples
{
    private static IStmt buildExample(IStmt... stmts)
    {
        if (stmts.length == 0)
        {
            return new NopStmt();
        }
        if (stmts.length == 1)
        {
            return stmts[0];
        }
        IStmt resStmt = new CompStmt(stmts[0], stmts[1]);
        for (int i = 2; i < stmts.length; ++i)
            resStmt = new CompStmt(resStmt, stmts[i]);
        return resStmt;
    }

    public static IStmt[] getExamples()
    {

        /*
         * int v;
         * v = 3;
         * print(v + 4);
         */
        IStmt example1 = buildExample
                         (
                            new VarDeclStmt("v", new IntType()),
                            new AssignStmt("v", new ValueExp(new IntValue(3))),
                            new PrintStmt(new ArithExp(Operation.ADDITION, new VarExp("v"), new ValueExp(new IntValue(4))))
                         );
        /*
        * int v;
        * Ref int a;
        * v = 10;
        * new(a, 22);
        * fork(
        *     writeHeap(a, 30);
        *     v = 32;
        *     print(v);
        *     print(readHeap(a));
        * )
        * print(v);
        * print(readHeap(a));
        */
        IStmt example2 = buildExample
                        (
                            new VarDeclStmt("v", new IntType()),
                            new VarDeclStmt("a", new RefType(new IntType())),
                            new AssignStmt("v", new ValueExp(new IntValue(10))),
                            new NewStmt("a", new ValueExp(new IntValue(22))),
                            new ForkStmt(
                                buildExample
                                (
                                    new WriteHeapStmt("a", new ValueExp(new IntValue(30))),
                                    new AssignStmt("v", new ValueExp(new IntValue(32))),
                                    new PrintStmt(new VarExp("v")),
                                    new PrintStmt(new ReadHeapExp(new VarExp("a")))
                                )
                            ),
                            new PrintStmt(new VarExp("v")),
                            new PrintStmt(new ReadHeapExp(new VarExp("a")))
                        );
        /*
         * bool counter;
         * Ref int a;
         * while (counter < 10)
         * {
         *      fork (
         *          fork (
         *              new(a, counter);
         *              print(readHeap(a));
         *          )
         *      )
         *      counter += 1
         * }
         */
        IStmt example3 = buildExample
                         (
                             new VarDeclStmt("counter", new IntType()),
                             new AssignStmt("counter", new ValueExp(new IntValue(0))),
                             new VarDeclStmt("a", new RefType(new IntType())),
                             new WhileStmt(new RelExp(Operation.LESS, new VarExp("counter"), new ValueExp(new IntValue(10))), buildExample(
                                           new ForkStmt(new ForkStmt(buildExample(
                                                    new NewStmt("a", new VarExp("counter")),
                                                    new PrintStmt(new ReadHeapExp(new VarExp("a")))
                                                )
                                            )),
                                            new AssignStmt("counter", new ArithExp(Operation.ADDITION, new VarExp("counter"), new ValueExp(new IntValue(1))))
                                        ))
                         );
        /*
         * v = 20;
         * for (v = 0; v < 3; v = v + 1) fork(print(v); v = v + 1);
         * print(v * 10)
         */
        IStmt example4 = buildExample
                         (
                             new VarDeclStmt("v", new IntType()),
                             new AssignStmt("v", new ValueExp(new IntValue(20))),
                             new ForStmt("v", new ValueExp(new IntValue(0)), new ValueExp(new IntValue(3)),
                                 new ArithExp(Operation.ADDITION, new VarExp("v"), new ValueExp(new IntValue(1))),
                                 new ForkStmt(buildExample(
                                     new PrintStmt(new VarExp("v")),
                                     new AssignStmt("v", new ArithExp(Operation.ADDITION, new VarExp("v"), new ValueExp(new IntValue(1))))
                                     )
                                 )
                             ),
                             new PrintStmt(new ArithExp(Operation.MULTIPLICATION, new VarExp("v"), new ValueExp(new IntValue(10))))
                         );
        /*
         * v = 0;
         * while (v < 3)
         * {
         *     fork (
         *         print(v);
         *         v = v + 1;
         *     )
         *     v = v + 1;
         * }
         * sleep(5);
         * print(v * 10);
         */
        IStmt example5 = buildExample
                         (
                             new VarDeclStmt("v", new IntType()),
                             new WhileStmt(new RelExp(Operation.LESS, new VarExp("v"), new ValueExp(new IntValue(3))), buildExample (
                                     new ForkStmt(buildExample(
                                             new PrintStmt(new VarExp("v")),
                                             new AssignStmt("v", new ArithExp(Operation.ADDITION, new VarExp("v"), new ValueExp(new IntValue(1))))
                                         )
                                     ),
                                     new AssignStmt("v", new ArithExp(Operation.ADDITION, new VarExp("v"), new ValueExp(new IntValue(1))))
                                 )
                             ),
                             new SleepStmt(5),
                             new PrintStmt(new ArithExp(Operation.MULTIPLICATION, new VarExp("v"), new ValueExp(new IntValue(10))))
                         );
        /*
         * v = 20;
         * wait(10);
         * print(v * 10);
         */
        IStmt example6 = buildExample
                         (
                             new VarDeclStmt("v", new IntType()),
                             new AssignStmt("v", new ValueExp(new IntValue(20))),
                             new WaitStmt(10),
                             new PrintStmt(new ArithExp(Operation.MULTIPLICATION, new VarExp("v"), new ValueExp(new IntValue(10))))
                         );
        /*
         * a = 1;
         * b = 2;
         * c = 5;
         * switch (a * 10)
         * {
         *     case (b * c): { print(a); print(b); }
         *     case (10): { print(100); print(200); }
         *     default: print(300);
         * }
         * print(300);
         */
        IStmt example7 = buildExample
                         (
                             new VarDeclStmt("a", new IntType()),
                             new VarDeclStmt("b", new IntType()),
                             new VarDeclStmt("c", new IntType()),
                             new AssignStmt("a", new ValueExp(new IntValue(1))),
                             new AssignStmt("b", new ValueExp(new IntValue(2))),
                             new AssignStmt("c", new ValueExp(new IntValue(5))),
                             new SwitchStmt(new ArithExp(Operation.MULTIPLICATION, new VarExp("a"), new ValueExp(new IntValue(10))),
                                            new ArithExp(Operation.MULTIPLICATION, new VarExp("b"), new VarExp("c")),
                                            new CompStmt(new PrintStmt(new VarExp("a")), new PrintStmt(new VarExp("b"))),
                                            new ValueExp(new IntValue(10)),
                                            new CompStmt(new PrintStmt(new ValueExp(new IntValue(100))), new PrintStmt(new ValueExp(new IntValue(200)))),
                                            new PrintStmt(new ValueExp(new IntValue(300)))
                                        ),
                             new PrintStmt(new ValueExp(new IntValue(300)))
                         );
         /*
          * bool b;
          * int c;
          * b = true;
          * c = b ? 100 : 200;
          * print(c);
          * c = false ? 100 : 200;
          * print(c);
          */
        IStmt example8 = buildExample
                         (
                             new VarDeclStmt("b", new BoolType()),
                             new VarDeclStmt("c", new IntType()),
                             new AssignStmt("b", new ValueExp(new BoolValue(true))),
                             new CondAssignStmt("c", new VarExp("b"), new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200))),
                             new PrintStmt(new VarExp("c")),
                             new CondAssignStmt("c", new ValueExp(new BoolValue(false)), new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200))),
                             new PrintStmt(new VarExp("c"))
                         );
        IStmt example9 = buildExample
                         (
                             new VarDeclStmt("a", new BoolType()),
                             new PrintStmt(new NotExp(new VarExp("a")))
                         );
        IStmt example10 = buildExample
                          (
                              new VarDeclStmt("v", new IntType()),
                              new RepeatUntilStmt(buildExample(
                                      new PrintStmt(new VarExp("v")),
                                      new AssignStmt("v", new ArithExp(Operation.ADDITION, new VarExp("v"), new ValueExp(new IntValue(1))))
                                  ),  new RelExp(Operation.EQUAL, new VarExp("v"), new ValueExp(new IntValue(5)))
                              )
                          );
        IStmt example11 = buildExample
                          (
                              new VarDeclStmt("a", new RefType(new IntType())),
                              new VarDeclStmt("v", new IntType()),
                              new NewStmt("a", new ValueExp(new IntValue(20))),
                              new ForStmt("v", new ValueExp(new IntValue(0)), new ValueExp(new IntValue(3)),
                                  new ArithExp(Operation.ADDITION, new VarExp("v"), new ValueExp(new IntValue(1))),
                                  new ForkStmt(buildExample(
                                          new PrintStmt(new VarExp("v")),
                                          new AssignStmt("v", new ArithExp(Operation.MULTIPLICATION, new VarExp("v"), new ReadHeapExp(new VarExp("a"))))
                                      )
                                  )
                              ),
                              new PrintStmt(new ReadHeapExp(new VarExp("a")))
                          );
        
        return new IStmt[]{example1, example2, example3, example11};
    }
}
