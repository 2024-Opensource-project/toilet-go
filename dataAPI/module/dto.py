from dataclasses import dataclass
from typing import Optional

@dataclass
class HouseDetailDTO:
    type: Optional[str] = ""
    size: Optional[str] = ""
    supplyCount: Optional[int] = ""
    deposit: Optional[int] = ""
    monthlyRent: Optional[int] = ""
    imageUrl: Optional[str] = ""

@dataclass
class HouseDTO:
    name: Optional[str] = ""
    address: Optional[str] = ""
    status: str = "공고중"
    moveInDate: Optional[str] = ""
    applyStartDate: Optional[str] = ""
    applyEndDate: Optional[str] = ""
    company: Optional[str] = ""
    redirectUrlForm: Optional[str] = ""
    houseDetails: Optional[list[HouseDetailDTO]] = ""
