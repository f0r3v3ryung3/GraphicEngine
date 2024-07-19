Это ray tracing для создания красивых картинок.  
В проге все картинке сохраняются в формате bmp, а здесь картинки в jpg.  
Прога умеет рисовать кубы, конусы, шары, круги и плоскости в невероятном качесте. Свет который может быть 3 видов: точечный, постоянный и векторный.  
Проект был делан для себя, и он не адаптирован для того, чтобы другие разобрались в том как это работает, простите не успел)

**А вот то, что можно с помощью ее делать**
![output](https://github.com/f0r3v3ryung3/GraphicEngine/assets/113245371/63e055e1-30ee-4cb5-a826-922de30cf130)
![output-_1_](https://github.com/f0r3v3ryung3/GraphicEngine/assets/113245371/5c8896ff-e2e3-4643-b75d-ed9aa9ff3380)
![lol](https://github.com/f0r3v3ryung3/GraphicEngine/assets/113245371/fa6f0248-a5ff-488f-bf5a-6918c2cff433)
![output2](https://github.com/f0r3v3ryung3/GraphicEngine/assets/113245371/2c00cc79-9a80-4883-8d97-2a753d8bbbdf)

**Забавные фейлы**
![output1](https://github.com/f0r3v3ryung3/GraphicEngine/assets/113245371/d56f8a25-d306-4d7f-80b7-ee946bf81ebc)
![output3](https://github.com/f0r3v3ryung3/GraphicEngine/assets/113245371/38df31ab-2802-46ab-ba75-318f50a0cf4d)

Как писать промпты:

обозначения для объектов и их типы:
```
color Color[x1[double]], x2[double], x3[double]
point Point[x[double], y[double], z[double]]
vector Vector[x[double], y[double], z[double]]

light_amb imp[double]
light_dir vec[Vector[x[double], y[double], z[double]], imp[double]
light_point point[Point[x[double], y[double], z[double]], imp[double]]

sphere center[Point], r[double], color[Color], specular[double], reflective[double]
plane normal[Vector], center[Point], color[Color], specular[double], reflective[double]
square length[double], center[Point], vec_A[Vector], vec_B[Vector], color[Color], specular[double], reflective[double]
circle normal[Vector], center[Point], r[double], color[Color], s[double], rf[double]
cone cone_height[Vector], center[Point], angle[double], length[double], color[Color], specular[double], reflective[double]
cube length[double], center[Point], vec_A[Vector], vec_B[Vector], color[Color], specular[double], reflective[double]
```

Формата ввода данных:
```
width[int]
height[int]

n_light[int]
light_amb imp[double]
light_dir x[double] y[double] z[double] imp[double]
light_point x[double] y[double] z[double] imp[double]

n_obj[int]
sphere x[double], y[double], z[double], r[double], x1[double], x2[double], x3[double], specular[double], reflective[double]
plane x_N[double], y_N[double], z_N[double], x[double], y[double], z[double], x1[double], x2[double], x3[double], specular[double], reflective[double]
```

пример:
```
1000
1000
3
light_amb 0,2
light_dir -1 -1 -1 0,4
light_point 0 10 10 0,4
4
cone -1 1 1 -1 -1 6 0,3 2 0 255 255 500 0,4
plane 0 1 0 0 -1,5 0 255 0 0 300 0,6
sphere 2 1 7 1 0 255 0 300 0,2
cube 2 0 4 12 0 1 0 -2 0 1 0 0 255 100 0,1
```
результат:
![output](https://github.com/user-attachments/assets/d0e61279-01e2-458a-8864-762096685031)
