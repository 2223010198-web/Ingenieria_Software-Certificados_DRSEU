use superStore_DB
go

create view vw_DataPowerBI
as
select
    v.OrderID,
    v.OrderDate,
    c.CustomerName,
    c.Segment,
    p.ProductName,
    p.Category,
    p.SubCategory,
    u.City,
    u.State,
    u.Region,
    v.Sales,
    v.Quantity,
    v.Discount,
    v.Profit,
    v.ShipMode,
    v.ShipDate,
    v.ShippingDays,
    dbo.fn_ClasificarVenta(v.Sales) as CategoriaVenta
from Fact_Ventas v
inner join Dim_Clientes c on v.CustomerID = c.CustomerID
inner join Dim_Productos p on v.ProductID = p.ProductID
inner join Dim_Ubicacion u on v.UbicacionID = u.UbicacionID;
go

/*drop view vw_DataPowerBI*/


