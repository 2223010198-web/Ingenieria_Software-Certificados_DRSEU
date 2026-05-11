use superStore_DB
go


/*Tabla General para la importacion de la Data del archivo Excel*/

create table superStore_registros(
	RowID int primary key,           
    OrderID nvarchar(50),             
    OrderDate date,                  
    ShipDate date,                   
    ShipMode nvarchar(50),            
    CustomerID nvarchar(20),          
    CustomerName nvarchar(100),       
    Segment nvarchar(50),             
    Country nvarchar(100),            
    City nvarchar(100),               
    State nvarchar(100),              
    PostalCode nvarchar(20),          
    Region nvarchar(50),              
    ProductID nvarchar(50),           
    Category nvarchar(50),            
    SubCategory nvarchar(50),         
    ProductName nvarchar(255),        
    Sales decimal(18, 4),            
    Quantity int,                    
    Discount decimal(4, 2),          
    Profit decimal(18, 4),
    ShippingDays int
)
/*drop table superStore_registros*/


--Creacion de las Tablas--

/*Tabla cliente(customer)*/

create table Dim_Clientes(
	CustomerID nvarchar(20) not null,
	CustomerName nvarchar(200),
	Segment nvarchar(20)
)
go
/*drop table Dim_Clientes*/

/*Tabla Region*/

create table Dim_Ubicacion(
	UbicacionID int identity(1,1) ,
	Country nvarchar(20),
	City nvarchar(20),
	State nvarchar(20),
	PostalCode nvarchar(20),
	Region nvarchar(20)
)
go
/*drop table Dim_Ubicacion*/


/*Tabla Producto(Product)*/

create table Dim_Productos(
	ProductID nvarchar(20) not null ,
    ProductName nvarchar(200),
    Category nvarchar(30),
    SubCategory nvarchar(30)
)
go
/*drop table Dim_Productos*/

/*Tabla Ventas*/

create table Fact_Ventas(
    RowID int not null , 
    OrderID nvarchar(25),
    OrderDate date,
    CustomerID nvarchar(20) ,
    ProductID nvarchar(20),
    UbicacionID int,
    Sales decimal(18,2),
    Quantity int,
    Discount decimal(5,2),
    Profit decimal(18,2),
    ShipMode nvarchar(20),
    ShipDate date,
    ShippingDays int
)
go
/*drop table Fact_Ventas*/


--Creacion de las primary keys--

/*Para la tabla Clientes*/
alter table Dim_Clientes
add constraint PK_Dim_Clientes
primary key (CustomerID)
go


/*Para la tabla Ubicacion*/
alter table Dim_Ubicacion
add constraint PK_Ubicacion
primary key (UbicacionID)
go

/*Para la tabla Productos*/
alter table Dim_Productos
add constraint PK_Productos
primary key (ProductID)
go

/*Para la tabla Ventas*/
alter table Fact_Ventas
add constraint PK_Ventas
primary key (RowID)
go

/*alter table Dim_Clientes
drop constraint PK_Dim_Clientes*/

/*alter table Dim_Ubicacion
drop constraint PK_Ubicacion*/

/*alter table Dim_Productos
drop constraint PK_Productos*/

/*alter table Fact_Ventas
drop constraint PK_Ventas*/

--Creacoin de las Foreign Keys--

/*Para ventas-clientes*/
alter table Fact_Ventas
add constraint FK_Ventas_Clientes
foreign key (CustomerID)
references Dim_Clientes(CustomerID)
go

/*alter table Fact_Ventas
drop constraint FK_Ventas_Clientes*/

/*Para ventas-Productos*/
alter table Fact_Ventas
add constraint FK_Ventas_Productos
foreign key (ProductID)
references Dim_Productos(ProductID)
go

/*alter table Fact_Ventas
drop constraint FK_Ventas_Productos*/

/*Para ventas-Ubicacion*/
alter table Fact_Ventas
add constraint FK_Ventas_Ubicacion
foreign key (UbicacionID)
references Dim_Ubicacion(UbicacionID)
go

/*alter table Fact_Ventas
drop constraint FK_Ventas_Ubicacion*/









