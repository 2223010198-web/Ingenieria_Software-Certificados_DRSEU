import pandas as pd 


df = pd.read_csv("Superstore.csv", encoding="latin1")   

df.isnull().sum()
df.duplicated(subset=["Order ID", "Product ID"]).sum()
df["Order Date"] = pd.to_datetime(df["Order Date"])
df["Ship Date"] = pd.to_datetime(df["Ship Date"])
df["Shipping Days"] = (df["Ship Date"] - df["Order Date"]).dt.days


print(df.info())
print(df[["Order Date", "Ship Date", "Shipping Days"]].head())


df.to_excel("Superstore_Limpiado.xlsx", index=False)

