from dataclasses import dataclass
from typing import Optional

@dataclass
class HouseDTO:
    name: Optional[str] = "미정"
    description: Optional[str] = "미정"
    price: Optional[str] = "미정"
    count: Optional[str] = "미정"
    address: Optional[str] = "미정"
    imageUrl: Optional[str] = "미정"
    status: Optional[str] = "미정"
    movingDate: Optional[str] = "미정"
    submissionDate: Optional[str] = "미정"
